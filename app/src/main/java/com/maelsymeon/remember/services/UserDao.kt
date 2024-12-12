package com.maelsymeon.remember.services

import androidx.room.*
import com.maelsymeon.remember.entities.UserEntity
import com.maelsymeon.remember.entities.UserWithCapsules

@Dao
interface UserDao {
    @Transaction
    @Query("SELECT * FROM users")
    suspend fun getUsersWithCapsules(): List<UserWithCapsules>

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): UserEntity?

    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUser(user: UserEntity)

    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteById(userId: String)
}