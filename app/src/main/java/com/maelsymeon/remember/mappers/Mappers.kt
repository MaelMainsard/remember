package com.maelsymeon.remember.mappers

import com.maelsymeon.remember.entities.MediaEntity
import com.maelsymeon.remember.enums.MediaType
import com.maelsymeon.remember.models.Media
import java.util.UUID

fun Media.toEntity(capsuleId: String): MediaEntity {
    return MediaEntity(
        id = id.toString(),
        type = type.toString(),
        uri = uri,
        capsuleId = capsuleId
    )
}

fun MediaEntity.toModel(): Media {
    return Media(
        id = UUID.fromString(id),
        type = MediaType.valueOf(type),
        uri = uri
    )
}