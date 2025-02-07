package com.maelsymeon.remember.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.maelsymeon.remember.ui.components.LockedCard
import com.maelsymeon.remember.ui.components.UnlockedCard

@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Column {
                Text(
                    text = "Écran d'Accueil",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 0.dp),  // Pour s'assurer qu'il n'y a pas de padding par défaut
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(10) { index ->
                        UnlockedCard(
                            date = "2024-01-12",
                            title = "Kermes de titouan",
                            blue = index % 2 == 0,
                            modifier = Modifier.padding(start = if (index == 0) 0.dp else 8.dp)
                        )
                    }
                }
            }
        }
        item {
            Text(
                text = "Écran d'Accueil",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }

        // Éléments de test pour démontrer le défilement
        items(20) { index ->
            LockedCard(
                "Aniversaire de Paul",
                "2024-05-21",
            )
        }
    }
}