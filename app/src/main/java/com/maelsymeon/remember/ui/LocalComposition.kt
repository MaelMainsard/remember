package com.maelsymeon.remember.ui

import androidx.compose.runtime.staticCompositionLocalOf
import com.maelsymeon.remember.database.AppDatabase
import com.maelsymeon.remember.entities.UserWithCapsules
import com.maelsymeon.remember.models.User

val LocalDatabase = staticCompositionLocalOf<AppDatabase> {
    error("Database not provided")
}

val LocalUser = staticCompositionLocalOf<UserWithCapsules?> {
    null
}