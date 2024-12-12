package com.maelsymeon.remember.utils

import com.maelsymeon.remember.models.*;
import java.time.LocalDateTime

class CapsuleManager {

    fun getUnlockedCapsules(capsules: List<Capsule>): List<Capsule> {
        return capsules.filter { !it.isLocked }
    }

    fun getLockedCapsules(capsules: List<Capsule>): List<Capsule> {
        return capsules.filter { it.isLocked }
    }
}