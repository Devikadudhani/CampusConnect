package com.example.myapplication1.models

data class User(
    val name: String,              // User's full name
    val rollNumber: String,        // User's roll number
    val department: String,        // Department (e.g., Computer Science)
    val year: Int                  // Year of study (e.g., 1st year)
)