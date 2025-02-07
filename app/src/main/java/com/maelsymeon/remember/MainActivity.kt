package com.maelsymeon.remember

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.maelsymeon.remember.controllers.CapsuleController
import com.maelsymeon.remember.controllers.UserController
import com.maelsymeon.remember.database.AppDatabase
import com.maelsymeon.remember.entities.UserWithCapsules
import com.maelsymeon.remember.models.User
import com.maelsymeon.remember.ui.LocalDatabase
import com.maelsymeon.remember.ui.LocalUser
import com.maelsymeon.remember.ui.MainScreen
import com.maelsymeon.remember.ui.theme.RememberTheme
import kotlinx.coroutines.launch
import java.util.UUID


class MainActivity : ComponentActivity() {
    private lateinit var userController: UserController
    private lateinit var capsuleController: CapsuleController
    private var user by mutableStateOf<UserWithCapsules?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val userDao = database.userDao()
        val capsuleDao = database.capsuleDao()
        userController = UserController(userDao)
        capsuleController = CapsuleController(capsuleDao)

        lifecycleScope.launch {
            checkAndCreateUser()
            userController.getAllUsersWithCapsules().onSuccess { users ->
                user = users.firstOrNull()
            }
        }

        setContent {
            RememberTheme {
                CompositionLocalProvider(
                    LocalDatabase provides database,
                    LocalUser provides user
                ) {
                    MainScreen()
                }
            }
        }
    }

    private suspend fun checkAndCreateUser() {
        val result = userController.getAllUsers()

        if (!result.isSuccess) {
            val newUser = User(
                id = UUID.randomUUID(),
                username = "DefaultUser",
                email = "default@example.com"
            )
            userController.createOrUpdateUser(newUser)
        }
    }
}