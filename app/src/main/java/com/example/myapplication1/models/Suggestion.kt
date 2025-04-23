package com.example.myapplication1.models


data class UserInfo(
    val name: String,
    val rollNumber: String,
    val department: String,
    val year: String,
    val email: String
)

data class Suggestion(
    val message: String,
    val timestamp: Long = System.currentTimeMillis(), // time of posting
    val postedBy: UserInfo,
    val supporters: MutableList<UserInfo> = mutableListOf()
)