package com.example.muhammad_irfan_ramadhan_124140159_pertemuan3

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

// 1. Data class ProfileUiState
data class ProfileUiState(
    val name: String = "Muhammad Irfan Ramadhan",
    val bio: String = "saya adalah mahasiswa aktif Computer Science semester 5 di Institut Teknologi Sumatera",
    val email: String = "muhammad.124140159@student.itera.ac.id",
    val phone: String = "0895-0944-5111",
    val location: String = "Gg. Kemang, Way Hui, Kec. Jati Agung, Kabupaten Lampung Selatan, Lampung 35136",
    val isDarkMode: Boolean = false,
    val isEditing: Boolean = false,
    val draftName: String = name,
    val draftBio: String = bio
)

// 1. ProfileViewModel dengan StateFlow
class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    // 3. State dark mode disimpan di ViewModel
    fun toggleDarkMode(checked: Boolean) {
        _uiState.update { it.copy(isDarkMode = checked) }
    }

    // 2. Mulai edit: salin name/bio ke draft
    fun startEdit() {
        _uiState.update { it.copy(isEditing = true, draftName = it.name, draftBio = it.bio) }
    }

    fun cancelEdit() {
        _uiState.update { it.copy(isEditing = false) }
    }

    // 2. State hoisting: TextField hanya teruskan event ke sini
    fun updateDraftName(value: String) {
        _uiState.update { it.copy(draftName = value) }
    }

    fun updateDraftBio(value: String) {
        _uiState.update { it.copy(draftBio = value) }
    }

    // 2. Save button yang update ViewModel
    fun saveEdit() {
        _uiState.update {
            it.copy(
                name = it.draftName.ifBlank { it.name },
                bio = it.draftBio.ifBlank { it.bio },
                isEditing = false
            )
        }
    }
}
