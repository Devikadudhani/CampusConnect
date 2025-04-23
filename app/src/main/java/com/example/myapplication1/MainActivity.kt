
package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Launch RoleSelectionActivity immediately
        val intent = Intent(this, RoleSelectionActivity::class.java)
        startActivity(intent)
        finish() // Optional: Close MainActivity if you don't need it
    }
}