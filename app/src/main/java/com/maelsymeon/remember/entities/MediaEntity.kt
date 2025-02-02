package com.maelsymeon.remember.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.maelsymeon.remember.enums.MediaType
import com.maelsymeon.remember.models.Media
import com.maelsymeon.remember.models.User
import java.util.UUID

@Entity(
    tableName = "media",
    foreignKeys = [
        ForeignKey(
            entity = CapsuleEntity::class,
            parentColumns = ["id"],
            childColumns = ["capsuleId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MediaEntity(
    @PrimaryKey
    val id: String,
    val type: String,
    val uri: String,
    val capsuleId: String
) {
    fun toModel(): Media {
        return Media(
            id = UUID.fromString(id),
            type = MediaType.valueOf(type),
            uri = uri
        )
    }
}

// Extension from Media to MediaEntity
fun Media.toEntity(capsuleId: String) = MediaEntity(
    id = id.toString(),
    type = type.name,
    uri = uri,
    capsuleId = capsuleId
)
