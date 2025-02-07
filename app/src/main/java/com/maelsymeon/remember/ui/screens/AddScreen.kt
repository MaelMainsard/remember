package com.maelsymeon.remember.ui.screens

import android.Manifest
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.maelsymeon.remember.controllers.CapsuleController
import com.maelsymeon.remember.controllers.MediaController
import com.maelsymeon.remember.controllers.UserController
import com.maelsymeon.remember.enums.MediaType
import com.maelsymeon.remember.models.Capsule
import com.maelsymeon.remember.models.Media
import com.maelsymeon.remember.ui.LocalDatabase
import com.maelsymeon.remember.ui.components.TitledInputField
import com.maelsymeon.remember.ui.viewmodels.AddScreenViewModel
import com.maelsymeon.remember.ui.viewmodels.AddScreenViewModelFactory
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.draw.clip
import coil.compose.AsyncImage
import com.maelsymeon.remember.ui.LocalUser

@Composable
fun AddScreen(
    onCapsuleCreated: () -> Unit = {},
    viewModel: AddScreenViewModel = viewModel(
        factory = AddScreenViewModelFactory(
            CapsuleController(LocalDatabase.current.capsuleDao()),
            MediaController(LocalContext.current)
        )
    )
) {
    val user = LocalUser.current
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedMediaUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val context = LocalContext.current

    val imageVideoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 10)
    ) { uris ->
        selectedMediaUris = uris
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            imageVideoLauncher.launch(PickVisualMediaRequest(
                mediaType = ActivityResultContracts.PickVisualMedia.ImageAndVideo
            ))
        } else {
            Toast.makeText(context, "Permission needed to access media", Toast.LENGTH_LONG).show()
        }
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitledInputField(
            title = "Capsule title",
            placeholder = "Add a name to your capsule",
            value = title,
            onValueChange = { title = it }
        )

        TitledInputField(
            title = "Description",
            placeholder = "Add a description",
            value = description,
            onValueChange = { description = it }
        )

        Button(
            onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(arrayOf(
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.READ_MEDIA_VIDEO
                    ))
                } else {
                    permissionLauncher.launch(arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ))
                }
            }
        ) {
            Text("Select Media")
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(selectedMediaUris) { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }

        Button(
            onClick = {
                scope.launch {
                    try {
                        user?.user?.id?.toString()?.let { userId ->
                            Toast.makeText(context, "Saving media...", Toast.LENGTH_SHORT).show()

                            val mediaList = mutableListOf<Media>()

                            selectedMediaUris.forEach { uri ->
                                val type = when {
                                    uri.toString().contains("image") -> MediaType.IMAGE
                                    uri.toString().contains("video") -> MediaType.VIDEO
                                    else -> MediaType.IMAGE
                                }

                                viewModel.mediaController.saveMedia(uri, type).onSuccess {
                                    mediaList.add(it)
                                }.onFailure { exception ->
                                    Toast.makeText(context, "Error saving media: ${exception.message}", Toast.LENGTH_LONG).show()
                                    return@launch
                                }
                            }

                            Toast.makeText(context, "Creating capsule...", Toast.LENGTH_SHORT).show()

                            val capsule = Capsule(
                                title = title,
                                description = description,
                                unlockDate = LocalDateTime.now().plusDays(1),
                                mediaList = mediaList
                            )

                            viewModel.createCapsule(capsule, userId).onSuccess {
                                Toast.makeText(context, "Capsule created successfully!", Toast.LENGTH_LONG).show()
                                onCapsuleCreated()
                            }.onFailure { exception ->
                                Toast.makeText(context, "Error creating capsule: ${exception.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            },
            enabled = title.isNotBlank() && selectedMediaUris.isNotEmpty() && user != null
        ) {
            Text("Create Capsule")
        }
    }
}