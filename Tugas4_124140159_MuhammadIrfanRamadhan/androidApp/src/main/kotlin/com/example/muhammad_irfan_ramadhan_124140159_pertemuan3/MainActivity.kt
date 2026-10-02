package com.example.muhammad_irfan_ramadhan_124140159_pertemuan3

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: ProfileViewModel =
                androidx.lifecycle.viewmodel.compose.viewModel { ProfileViewModel() }
            val uiState by viewModel.uiState.collectAsState()

            // Kunci warna ikon status bar mengikuti dark mode APLIKASI
            // (bukan tema sistem): dark -> ikon putih, light -> ikon hitam
            val view = LocalView.current
            SideEffect {
                val window = (view.context as Activity).window
                val controller = WindowCompat.getInsetsController(window, view)
                controller.isAppearanceLightStatusBars = !uiState.isDarkMode
                controller.isAppearanceLightNavigationBars = !uiState.isDarkMode
            }

            App(viewModel)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}