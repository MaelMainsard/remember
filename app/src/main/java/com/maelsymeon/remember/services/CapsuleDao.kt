package com.maelsymeon.remember.services

import androidx.room.*
import com.maelsymeon.remember.entities.CapsuleEntity
import com.maelsymeon.remember.entities.CapsuleWithMedia

@Dao
interface CapsuleDao {
    @Transaction
    @Query("SELECT * FROM capsules")
    suspend fun getCapsulesWithMedia(): List<CapsuleWithMedia>

    @Query("SELECT * FROM capsules WHERE userId = :userId")
    suspend fun getCapsulesByUserId(userId: String): List<CapsuleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCapsule(capsule: CapsuleEntity)
}