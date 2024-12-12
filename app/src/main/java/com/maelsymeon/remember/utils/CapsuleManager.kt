package com.maelsymeon.remember.utils

import com.maelsymeon.remember.models.*;
import java.time.LocalDateTime

class CapsuleManager {
    fun checkLockStatus(capsule: Capsule): Boolean {
        return capsule.unlockDate.isAfter(LocalDateTime.now())
    }

    fun updateCapsuleStatus(capsule: Capsule) {
        capsule.isLocked = checkLockStatus(capsule)
    }

    fun updateAllCapsulesStatus(capsules: List<Capsule>) {
        capsules.forEach { updateCapsuleStatus(it) }
    }

    fun getUnlockedCapsules(capsules: List<Capsule>): List<Capsule> {
        return capsules.filter { !checkLockStatus(it) }
    }

    fun getLockedCapsules(capsules: List<Capsule>): List<Capsule> {
        return capsules.filter { checkLockStatus(it) }
    }
}