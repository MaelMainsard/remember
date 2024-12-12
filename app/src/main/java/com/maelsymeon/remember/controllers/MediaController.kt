package com.maelsymeon.remember.controllers

import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.Manifest
import com.maelsymeon.remember.enums.MediaType
import com.maelsymeon.remember.models.Media
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.UUID

class MediaController(
    private val context: Context
) {

    // Gestion des permissions
    private fun checkMediaPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED &&
                    context.checkSelfPermission(Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED
        } else {
            context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        }
    }

    // Traitement des médias via MediaStore
    suspend fun saveMedia(uri: Uri, type: MediaType): Result<Media> {
        if (!checkMediaPermissions()) {
            return Result.failure(SecurityException("Missing media permissions"))
        }

        return try {
            when (type) {
                MediaType.IMAGE -> saveImageToStore(uri)
                MediaType.VIDEO -> saveVideoToStore(uri)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Gestion du stockage pour les images
    private suspend fun saveImageToStore(uri: Uri): Result<Media> {
        return withContext(Dispatchers.IO) {
            val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            val details = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_${System.currentTimeMillis()}.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }

            val savedUri = context.contentResolver.insert(collection, details)
                ?: return@withContext Result.failure(IOException("Failed to save image"))

            context.contentResolver.openOutputStream(savedUri)?.use { outputStream ->
                context.contentResolver.openInputStream(uri)?.use { input ->
                    input.copyTo(outputStream)
                }
            }

            Result.success(Media(
                uri = savedUri.toString(),
                id = UUID.randomUUID(),
                type = MediaType.IMAGE
            ))
        }
    }

    // Gestion du stockage pour les vidéos
    private suspend fun saveVideoToStore(uri: Uri): Result<Media> {
        return withContext(Dispatchers.IO) {
            val collection = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            val details = ContentValues().apply {
                put(MediaStore.Video.Media.DISPLAY_NAME, "VID_${System.currentTimeMillis()}.mp4")
                put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
            }

            val savedUri = context.contentResolver.insert(collection, details)
                ?: return@withContext Result.failure(IOException("Failed to save video"))

            context.contentResolver.openOutputStream(savedUri)?.use { outputStream ->
                context.contentResolver.openInputStream(uri)?.use { input ->
                    input.copyTo(outputStream)
                }
            }

            Result.success(Media(
                uri = savedUri.toString(),
                id = UUID.randomUUID(),
                type = MediaType.VIDEO
            ))
        }
    }
}