package com.example.myapplication1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class StudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(android.R.layout.simple_list_item_1)
        title = "Student Screen"
    }
}
