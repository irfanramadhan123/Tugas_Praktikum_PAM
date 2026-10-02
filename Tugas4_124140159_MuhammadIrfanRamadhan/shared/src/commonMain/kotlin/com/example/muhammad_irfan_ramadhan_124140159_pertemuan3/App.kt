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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
fun App(viewModel: ProfileViewModel = androidx.lifecycle.viewmodel.compose.viewModel { ProfileViewModel() }) {
    val state by viewModel.uiState.collectAsState()

    MaterialTheme(
        colorScheme = if (state.isDarkMode) darkColorScheme() else lightColorScheme()
    ) {
        // Surface menyediakan content color (onBackground) yang otomatis
        // putih di dark mode dan hitam di light mode untuk semua Text di dalamnya
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 3. Switch dark/light mode
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (state.isDarkMode) "Light Mode" else "Dark Mode",
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Switch(
                    checked = state.isDarkMode,
                    onCheckedChange = { viewModel.toggleDarkMode(it) }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            ProfileHeader(name = state.name)

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Form edit nama dan bio (state hoisting)
            if (state.isEditing) {
                ProfileCard(title = "Edit Profile") {
                    OutlinedTextField(
                        value = state.draftName,
                        onValueChange = { viewModel.updateDraftName(it) },
                        label = { Text("Nama") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = state.draftBio,
                        onValueChange = { viewModel.updateDraftBio(it) },
                        label = { Text("Bio") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        Button(onClick = { viewModel.saveEdit() }) {
                            Text("Save")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { viewModel.cancelEdit() }) {
                            Text("Batal")
                        }
                    }
                }
            } else {
                ProfileCard(title = "Bio") {
                    Text(text = state.bio)
                }
            }

            ProfileCard(title = "Informasi") {
                InfoItem(label = "Email", value = state.email)
                InfoItem(label = "Phone", value = state.phone)
                InfoItem(label = "Location", value = state.location)
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (!state.isEditing) {
                Button(onClick = { viewModel.startEdit() }) {
                    Text("Edit Profile")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {}) {
                Text("Hubungi Saya")
            }
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
