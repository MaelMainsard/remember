package com.maelsymeon.remember.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val title: String
) {
    data object Home : BottomNavItem("home", Icons.Default.Home, "Accueil")
    data object Profile : BottomNavItem("profile", Icons.Default.Person, "Profil")
    data object Add : BottomNavItem("add", Icons.Default.AddCircle, "Nouvelle capsule")
}