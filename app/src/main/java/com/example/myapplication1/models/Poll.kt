package com.example.myapplication1.models

data class Poll(
    val question: String,
    val options: List<String>,
    val votes: MutableList<Vote> = mutableListOf()
)

data class Vote(
    val voterName: String,
    val voterRoll: String,
    val selectedOption: String
)