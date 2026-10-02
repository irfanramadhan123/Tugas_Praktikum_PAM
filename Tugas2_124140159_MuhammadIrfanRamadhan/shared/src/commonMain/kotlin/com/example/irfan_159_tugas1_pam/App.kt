package com.example.irfan_159_tugas1_pam

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun App(viewModel: NewsViewModel = androidx.lifecycle.viewmodel.compose.viewModel { NewsViewModel() }) {
    MaterialTheme {
        val filtered by viewModel.filteredNews.collectAsState()
        val readCount by viewModel.readCount.collectAsState()
        val selectedCat by viewModel.category.collectAsState()
        val detail by viewModel.detail.collectAsState()
        val loadingId by viewModel.loadingId.collectAsState()
        val readIds by viewModel.readIds.collectAsState()
        val filters = listOf("Semua") + NewsRepository.categories

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .safeContentPadding()
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Text("News Feed Simulator", style = MaterialTheme.typography.headlineSmall)
            Text("Muhammad Irfan Ramadhan - 124140159")
            Text("Sudah dibaca: $readCount berita", style = MaterialTheme.typography.bodyMedium)

            Spacer(Modifier.height(8.dp))

            // 2. Filter berita berdasarkan kategori
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filters.forEach { cat ->
                    FilterChip(
                        selected = selectedCat == cat,
                        onClick = { viewModel.selectCategory(cat) },
                        label = { Text(cat) }
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // List berita dari Flow tiap 2 detik
            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filtered, key = { it.id }) { item ->
                    val isRead = readIds.contains(item.id)
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(12.dp)) {
                            Text(item.displayTitle, style = MaterialTheme.typography.bodyLarge)
                            Text(
                                if (isRead) "Status: sudah dibaca" else "Status: belum dibaca",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(Modifier.height(4.dp))
                            Button(onClick = {
                                // cari News asli by id untuk fetch detail async
                                viewModel.loadDetail(
                                    News(
                                        id = item.id,
                                        title = item.displayTitle,
                                        category = item.category
                                    )
                                )
                            }) {
                                Text(if (loadingId == item.id) "Loading..." else "Detail")
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            Text("Detail berita (async):", style = MaterialTheme.typography.titleMedium)
            Card(modifier = Modifier.fillMaxWidth()) {
                Text(detail, modifier = Modifier.padding(12.dp))
            }
        }
    }
}