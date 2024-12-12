package com.maelsymeon.remember.models
import com.maelsymeon.remember.entities.UserEntity
import java.util.UUID

data class User(
    val id: UUID = UUID.randomUUID(),
    val username: String,
    val email: String,
    val capsules: MutableList<Capsule> = mutableListOf()
) {
    fun toEntity(): UserEntity {
        return UserEntity(
            id = id.toString(),
            username = username,
            email = email
        )
    }
}