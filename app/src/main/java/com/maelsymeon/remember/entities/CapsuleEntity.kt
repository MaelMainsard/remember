package com.maelsymeon.remember.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.maelsymeon.remember.models.Capsule
import com.maelsymeon.remember.models.Media
import java.time.LocalDateTime
import java.util.UUID

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
    val userId: String  // Foreign key to User
) {
    fun toModel(): Capsule {
        return Capsule(
            id = UUID.fromString(id),
            title = title,
            description = description,
            creationDate = LocalDateTime.parse(creationDate),
            unlockDate = LocalDateTime.parse(unlockDate),
            mediaList = mutableListOf() // La liste des médias sera chargée séparément
        )
    }

    val isLocked: Boolean
        get() = LocalDateTime.parse(unlockDate).isAfter(LocalDateTime.now())
}

// Extension from Capsule to CapsuleEntity
fun Capsule.toEntity(userId: String) = CapsuleEntity(
    id = id.toString(),
    title = title,
    description = description,
    creationDate = creationDate.toString(),
    unlockDate = unlockDate.toString(),
    userId = userId
)
