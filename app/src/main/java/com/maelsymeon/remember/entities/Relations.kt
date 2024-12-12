package com.maelsymeon.remember.entities

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithCapsules(
    @Embedded
    val user: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val capsules: List<CapsuleEntity>
)

data class CapsuleWithMedia(
    @Embedded
    val capsule: CapsuleEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "capsuleId"
    )
    val media: List<MediaEntity>
)