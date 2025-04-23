package com.example.myapplication1.models

data class Feedback(
    val message: String = "",
    val rating: Int = 0, // 1 to 5 stars
    val userName: String = "",
    val timestamp: String
)