
package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.ViewCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Launch RoleSelectionActivity immediately
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
        finish() // Optional: Close MainActivity if you don't need it
    }
}
