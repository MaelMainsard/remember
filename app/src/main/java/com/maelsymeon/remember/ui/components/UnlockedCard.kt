package com.maelsymeon.remember.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maelsymeon.remember.ui.theme.Blue10
import com.maelsymeon.remember.ui.theme.Gray20

@Composable
fun UnlockedCard(
    date: String,
    title: String,
    blue: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = if (blue) Blue10 else Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.spacedBy(-(6).dp)
        ) {
            Text(
                text = "Unlocked the $date",
                color = if (blue) Color.White else Gray20,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
            Text(
                text = title,
                color = if (blue) Color.White else Color.Black,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
            )
        }
    }
}