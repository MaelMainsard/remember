package com.maelsymeon.remember.services

import androidx.room.*
import com.maelsymeon.remember.entities.CapsuleEntity
import com.maelsymeon.remember.entities.CapsuleWithMedia
import com.maelsymeon.remember.entities.UserEntity

@Dao
interface CapsuleDao {
    @Transaction
    @Query("SELECT * FROM capsules")
    suspend fun getCapsulesWithMedia(): List<CapsuleWithMedia>

    @Query("SELECT * FROM capsules WHERE id = :capsuleId")
    suspend fun getCapsuleById(capsuleId: String): CapsuleEntity?

    @Query("SELECT * FROM capsules WHERE userId = :userId")
    suspend fun getCapsulesByUserId(userId: String): List<CapsuleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateCapsule(capsule: CapsuleEntity)

    @Query("DELETE FROM capsules WHERE id = :capsuleId")
    suspend fun deleteById(capsuleId: String)
}