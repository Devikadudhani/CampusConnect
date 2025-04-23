package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.myapplication1.AdminProfileActivity
import com.example.myapplication1.AdminDashboardActivity

class AdminProfileActivity : AppCompatActivity() {

    private lateinit var logoutButton: Button
    private lateinit var bottomNavBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_profile)


        // Initialize bottom navigation bar
        bottomNavBar = findViewById(R.id.bottomNavBar)
        bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    // Navigate to Dashboard
                    navigateToDashboard()
                    true
                }
                R.id.nav_profile -> {
                    // Stay on the Profile Page
                    true
                }
                else -> false
            }
        }

        // Initialize logout button
        logoutButton = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            // Logout functionality will be implemented later
            // For now, just show a message or navigate to the login screen
        }
    }

    // Navigate to Dashboard screen
    private fun navigateToDashboard() {
        val intent = Intent(this, AdminDashboardActivity::class.java)
        startActivity(intent)
        finish() // Close current activity (Profile) and go to Dashboard
    }
}