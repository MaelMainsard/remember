package com.maelsymeon.remember.models

import com.maelsymeon.remember.entities.CapsuleEntity
import java.util.UUID
import java.time.LocalDateTime

data class Capsule(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String,
    val creationDate: LocalDateTime = LocalDateTime.now(),
    val unlockDate: LocalDateTime,
    var isLocked: Boolean = true,
    val mediaList: MutableList<Media> = mutableListOf()
) {
    fun toEntity(userId: String): CapsuleEntity {
        return CapsuleEntity(
            id = id.toString(),
            title = title,
            description = description,
            creationDate = creationDate.toString(),
            unlockDate = unlockDate.toString(),
            isLocked = isLocked,
            userId = userId
        )
    }
}