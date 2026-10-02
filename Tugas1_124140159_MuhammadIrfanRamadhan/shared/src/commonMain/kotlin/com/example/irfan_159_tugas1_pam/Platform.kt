package com.example.irfan_159_tugas1_pam

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform