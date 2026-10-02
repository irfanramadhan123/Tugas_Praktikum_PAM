package com.example.irfan_159_tugas1_pam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    // Filter kategori
    private val _category = MutableStateFlow("Semua")
    val category: StateFlow<String> = _category

    // 4. StateFlow untuk menyimpan jumlah berita yang sudah dibaca
    private val _readCount = MutableStateFlow(0)
    val readCount: StateFlow<Int> = _readCount

    private val _readIds = MutableStateFlow<Set<Int>>(emptySet())
    val readIds: StateFlow<Set<Int>> = _readIds

    private val _detail = MutableStateFlow("Pilih berita lalu tekan Detail.")
    val detail: StateFlow<String> = _detail

    private val _loadingId = MutableStateFlow<Int?>(null)
    val loadingId: StateFlow<Int?> = _loadingId

    private val _newsList = MutableStateFlow<List<News>>(emptyList())
    val newsList: StateFlow<List<News>> = _newsList

    // 2. Filter + 3. Transform data menjadi format yang ditampilkan
    val filteredNews: StateFlow<List<NewsDisplay>> =
        combine(_newsList, _category) { list, cat ->
            list
                .filter { cat == "Semua" || it.category == cat }
                .map {
                    NewsDisplay(
                        id = it.id,
                        // transform: uppercase kategori + format judul
                        displayTitle = "[${it.category.uppercase()}] ${it.title}",
                        category = it.category
                    )
                }
        }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    init {
        // kumpulkan Flow berita tiap 2 detik
        viewModelScope.launch {
            NewsRepository.newsFeed().collect { news ->
                _newsList.update { it + news }
            }
        }
    }

    fun selectCategory(cat: String) {
        _category.value = cat
    }

    fun markRead(id: Int) {
        if (!_readIds.value.contains(id)) {
            _readIds.update { it + id }
            _readCount.update { it + 1 }
        }
    }

    fun loadDetail(news: News) {
        viewModelScope.launch {
            _loadingId.value = news.id
            val result = NewsRepository.fetchDetail(news)
            _detail.value = result
            markRead(news.id)
            _loadingId.value = null
        }
    }
}
