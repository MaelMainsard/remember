package com.maelsymeon.remember.models
import com.maelsymeon.remember.enums.MediaType
import java.util.UUID

data class Media(
    val id: UUID = UUID.randomUUID(),
    val type: MediaType,
    val uri: String
)