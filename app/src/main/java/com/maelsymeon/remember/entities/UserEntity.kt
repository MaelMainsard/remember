package com.maelsymeon.remember.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maelsymeon.remember.models.User
import java.util.UUID

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val username: String,
    val email: String
) {
    fun toModel(): User {
        return User(
            id = UUID.fromString(id),
            username = username,
            email = email
        )
    }
}

// Extension from User to UserEntity
fun User.toEntity() = UserEntity(
    id = id.toString(),
    username = username,
    email = email,
)
