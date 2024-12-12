package com.maelsymeon.remember.models

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
)