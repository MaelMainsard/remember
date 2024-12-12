package com.maelsymeon.remember.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "capsules",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CapsuleEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val creationDate: String,
    val unlockDate: String,
    var isLocked: Boolean,
    val userId: String  // Foreign key to User
)