package com.maelsymeon.remember

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.maelsymeon.remember.database.AppDatabase
import com.maelsymeon.remember.entities.*
import com.maelsymeon.remember.ui.theme.RememberTheme
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Tester la base de données
        testDatabase()

        setContent {
            RememberTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun testDatabase() {
        lifecycleScope.launch {
            try {
                // Obtenir l'instance de la base de données
                val db = AppDatabase.getDatabase(applicationContext)

                // Créer un utilisateur test
                val user = UserEntity(
                    id = UUID.randomUUID().toString(),
                    username = "TestUser",
                    email = "test@example.com"
                )

                // Créer une capsule test
                val capsule = CapsuleEntity(
                    id = UUID.randomUUID().toString(),
                    title = "Ma deuxième capsule",
                    description = "Une description de test",
                    creationDate = LocalDateTime.now().toString(),
                    unlockDate = LocalDateTime.now().plusDays(1).toString(),
                    userId = user.id
                )

                // Créer un media test
                val media = MediaEntity(
                    id = UUID.randomUUID().toString(),
                    type = "IMAGE",
                    uri = "content://test/photo.jpg",
                    capsuleId = capsule.id
                )

                // Insérer les données
                db.userDao().insertOrUpdateUser(user)
                
                db.capsuleDao().insertOrUpdateCapsule(capsule)

                db.mediaDao().insertMedia(media)

                // Vérifier les relations
                val userWithCapsules = db.userDao().getUsersWithCapsules()
                val capsuleWithMedia = db.capsuleDao().getCapsulesWithMedia()

                // Afficher les résultats
                Log.d("DatabaseTest", "User with capsules: $userWithCapsules")
                Log.d("DatabaseTest", "Capsule with media: $capsuleWithMedia")

                // Afficher un résumé plus lisible
                displayDatabaseContent(userWithCapsules)

            } catch (e: Exception) {
                Log.e("DatabaseTest", "Erreur lors du test de la base de données", e)
            }
        }
    }

    private fun displayDatabaseContent(userWithCapsules: List<UserWithCapsules>) {
        userWithCapsules.forEach { userWithCapsules ->
            Log.d("DatabaseTest", """
                Utilisateur: ${userWithCapsules.user.username}
                Email: ${userWithCapsules.user.email}
                Nombre de capsules: ${userWithCapsules.capsules.size}
                
                Détails des capsules:
                ${userWithCapsules.capsules.joinToString("\n") { capsule ->
                "- ${capsule.title} (${if (capsule.toModel().isLocked) "Verrouillée" else "Déverrouillée"})"
            }}
            """.trimIndent())
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RememberTheme {
        Greeting("Android")
    }
}