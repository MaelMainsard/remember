package com.maelsymeon.remember.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.maelsymeon.remember.entities.UserEntity
import com.maelsymeon.remember.services.UserDao
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest : TestCase() {

    private lateinit var db:AppDatabase
    private lateinit var dao: UserDao

//    Ne pas oublier de mettre ce code dans le build.gradle.kts app dans android à la suite de build feature :
//
//    packaging {
//        resources {
//            excludes.addAll(listOf(
//                "META-INF/LICENSE.md",
//                "META-INF/LICENSE-notice.md",
//                "META-INF/DEPENDENCIES",
//                "META-INF/LICENSE",
//                "META-INF/LICENSE.txt",
//                "META-INF/NOTICE",
//                "META-INF/NOTICE.txt"
//            ))
//        }
//    }


    private val user = UserEntity(
        id = UUID.randomUUID().toString(),
        username = "User Test",
        email = "test@example.com"
    )

    @Before
    public override fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.userDao()
    }

    @After
    fun closeDb(){
        db.close()
    }

    @Test
    fun createUser() = runBlocking {

        dao.insertOrUpdateUser(user)

        val savedUser = dao.getUserById(user.id)
        assertNotNull(savedUser)
        assertEquals(user.id, savedUser?.id)
        assertEquals(user.email, savedUser?.email)
    }

    @Test
    fun readUser() = runBlocking {

        dao.insertOrUpdateUser(user)

        val retrievedUser = dao.getUserById(user.id)


        assertNotNull(retrievedUser)
        assertEquals(user.email, retrievedUser?.email)
        assertEquals(user.username, retrievedUser?.username)
    }

    @Test
    fun updateUser() = runBlocking {

        dao.insertOrUpdateUser(user)

        val updatedUser = user.copy(username = "updatedUsername")
        dao.insertOrUpdateUser(updatedUser)

        val retrievedUser = dao.getUserById(user.id)
        assertEquals("updatedUsername", retrievedUser?.username)
    }

    @Test
    fun deleteUser() = runBlocking {

        dao.insertOrUpdateUser(user)

        dao.deleteById(user.id)

        val deletedUser = dao.getUserById(user.id)
        assertNull(deletedUser)
    }
}