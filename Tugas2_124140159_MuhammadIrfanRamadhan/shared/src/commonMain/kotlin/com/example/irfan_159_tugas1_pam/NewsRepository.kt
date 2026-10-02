package com.example.irfan_159_tugas1_pam

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class News(
    val id: Int,
    val title: String,
    val category: String
)

data class NewsDisplay(
    val id: Int,
    val displayTitle: String,
    val category: String
)

object NewsRepository {
    val categories = listOf("Teknologi", "Olahraga", "Politik")

    // 1. Flow yang mensimulasikan data berita baru setiap 2 detik
    fun newsFeed(): Flow<News> = flow {
        var i = 1
        while (true) {
            val cat = categories[(i - 1) % categories.size]
            emit(News(id = i, title = "Berita #$i seputar $cat", category = cat))
            i++
            delay(2000)
        }
    }

    // 5. Coroutines untuk mengambil detail berita secara async
    suspend fun fetchDetail(news: News): String {
        delay(800) // simulasi network call
        return "Detail ${news.title}\nKategori: ${news.category}\nIni adalah konten lengkap berita secara async."
    }
}
