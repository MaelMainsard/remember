package com.maelsymeon.remember.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.maelsymeon.remember.ui.LocalUser


@Composable
fun ProfileScreen() {
    val user = LocalUser.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = user?.capsules.toString(),
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
    }
}