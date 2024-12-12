package com.maelsymeon.remember.models
import com.maelsymeon.remember.entities.MediaEntity
import com.maelsymeon.remember.enums.MediaType
import java.util.UUID

data class Media(
    val id: UUID = UUID.randomUUID(),
    val type: MediaType,
    val uri: String
) {
    fun toEntity(capsuleId: String): MediaEntity {
        return MediaEntity(
            id = id.toString(),
            type = type.name,
            uri = uri,
            capsuleId = capsuleId
        )
    }
}