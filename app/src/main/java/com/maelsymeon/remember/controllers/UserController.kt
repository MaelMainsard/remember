package com.maelsymeon.remember.controllers

import com.maelsymeon.remember.models.User
import com.maelsymeon.remember.services.UserDao

class UserController(
    private val userDao: UserDao
){
    suspend fun createOrUpdateUser(user: User): Result<User> {
        return try {
            validateUser(user)
            userDao.insertOrUpdateUser(user.toEntity())
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUser(id: String): Result<User> {
        return try {
            val userEntity = userDao.getUserById(id)
            userEntity?.let {
                Result.success(it.toModel())
            } ?: Result.failure(NoSuchElementException("User not found"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteUser(id: String): Result<Unit> {
        return try {
            userDao.deleteById(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun validateUser(user: User) {
        require(user.username.isNotBlank()) { "Username cannot be empty" }
        require(user.email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$"))) {
            "Invalid email format"
        }
    }
}