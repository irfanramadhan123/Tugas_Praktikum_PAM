package com.example.muhammad_irfan_ramadhan_124140159_pertemuan3

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource

import muhammad_irfan_ramadhan_124140159_pertemuan3.shared.generated.resources.Res
import muhammad_irfan_ramadhan_124140159_pertemuan3.shared.generated.resources.foto_irfan

@Composable
@Preview
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .safeContentPadding()
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProfileHeader(
                name = "Muhammad Irfan Ramadhan"
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProfileCard(title = "Bio") {
                Text(
                    text = "saya adalah mahasiswa aktif Computer Science semester 5 di Institut Teknologi Sumatera"
                )
            }

            ProfileCard(title = "Informasi") {
                InfoItem(
                    label = "Email",
                    value = "muhammad.124140159@student.itera.ac.id"
                )
                InfoItem(
                    label = "Phone",
                    value = "0895-0944-5111"
                )
                InfoItem(
                    label = "Location",
                    value = "Gg. Kemang, Way Hui, Kec. Jati Agung, Kabupaten Lampung Selatan, Lampung 35136"
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {}) {
                Text("Hubungi Saya")
            }
        }
    }
}

@Composable
fun ProfileHeader(name: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.foto_irfan),
                contentDescription = "Foto Profil",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(40.dp)
                .background(
                    MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ProfileCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}
