package com.maelsymeon.remember.services

import androidx.room.*
import com.maelsymeon.remember.entities.MediaEntity

@Dao
interface MediaDao {
    @Query("SELECT * FROM media WHERE capsuleId = :capsuleId")
    suspend fun getMediaByCapsuleId(capsuleId: String): List<MediaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedia(media: MediaEntity)

    @Delete
    suspend fun deleteMedia(media: MediaEntity)
}