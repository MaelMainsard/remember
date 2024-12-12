package com.maelsymeon.remember.models
import java.util.UUID

data class User(
    val id: UUID = UUID.randomUUID(),
    val username: String,
    val email: String,
    val capsules: MutableList<Capsule> = mutableListOf()
)