package com.maelsymeon.remember.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maelsymeon.remember.controllers.CapsuleController
import com.maelsymeon.remember.controllers.MediaController
import com.maelsymeon.remember.models.Capsule

class AddScreenViewModel(
    private val capsuleController: CapsuleController,
    val mediaController: MediaController
) : ViewModel() {

    suspend fun createCapsule(capsule: Capsule, userId: String): Result<Capsule> {
        return try {
            capsuleController.createOrUpdateCapsule(capsule, userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class AddScreenViewModelFactory(
    private val capsuleController: CapsuleController,
    private val mediaController: MediaController
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddScreenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddScreenViewModel(capsuleController, mediaController) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}