package com.maelsymeon.remember.controllers

import com.maelsymeon.remember.entities.toEntity
import com.maelsymeon.remember.models.Capsule
import com.maelsymeon.remember.services.CapsuleDao

class CapsuleController(
    private val capsuleDao: CapsuleDao
) {
    suspend fun createOrUpdateCapsule(capsule: Capsule, userId: String): Result<Capsule> {
        return try {
            validateCapsule(capsule)
            capsuleDao.insertOrUpdateCapsule(capsule.toEntity(userId = userId))
            Result.success(capsule)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCapsule(id: String): Result<Capsule> {
        return try {
            val capsuleEntity = capsuleDao.getCapsuleById(id)
            capsuleEntity?.let {
                Result.success(it.toModel())
            } ?: Result.failure(NoSuchElementException("Capsule not found"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteCapsule(id: String): Result<Unit> {
        return try {
            capsuleDao.deleteById(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun validateCapsule(capsule: Capsule) {
        require(capsule.title.isNotBlank()) { "Capsulename cannot be empty" }
        require(capsule.unlockDate.isAfter(capsule.creationDate)) {
            "Unlock date must be after the creation date"
        }
        requireNotNull(capsule.mediaList) { "Media list must not be null" }
    }
}