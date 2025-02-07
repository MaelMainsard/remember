package com.maelsymeon.remember.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.maelsymeon.remember.ui.components.BottomNavigationBar
import com.maelsymeon.remember.ui.navigation.BottomNavItem
import com.maelsymeon.remember.ui.screens.HomeScreen
import com.maelsymeon.remember.ui.screens.ProfileScreen
import com.maelsymeon.remember.ui.screens.AddScreen

@Composable
fun MainScreen() {
    var selectedItem by remember { mutableStateOf<BottomNavItem>(BottomNavItem.Home) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedItem = selectedItem,
                onItemSelected = { navItem ->
                    selectedItem = navItem
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedItem) {
                is BottomNavItem.Home -> HomeScreen()
                is BottomNavItem.Profile -> ProfileScreen()
                is BottomNavItem.Add -> AddScreen()
            }
        }
    }
}