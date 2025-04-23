package com.example.myapplication1.models

data class Complaint(
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val media: List<String> = listOf(),
    val user: User,
    val pendingPercentage:Int=0,
    val isAnonymous: Boolean = false
)