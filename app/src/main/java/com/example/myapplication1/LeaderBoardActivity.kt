package com.example.myapplication1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import com.example.myapplication1.adapters.LeaderboardAdapter
import com.example.myapplication1.models.Department
import com.google.android.material.bottomnavigation.BottomNavigationView

class LeaderboardActivity : AppCompatActivity() {

    private lateinit var leaderboardRecyclerView: RecyclerView
    private lateinit var leaderboardAdapter: LeaderboardAdapter
    private val departmentsList: MutableList<Department> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    // Avoid reopening the same activity if already on dashboard
                    if (this !is AdminDashboardActivity) {
                        val intent = Intent(this, AdminDashboardActivity::class.java)
                        startActivity(intent)
                        finish() // optional: to avoid backstack issues
                    }
                    true
                }

                R.id.nav_profile -> {
                    if (this !is AdminProfileActivity) {
                        val intent = Intent(this, AdminProfileActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    true
                }

                else -> false
            }
        }

        // Initialize RecyclerView
        leaderboardRecyclerView = findViewById(R.id.leaderboardRecyclerView)
        leaderboardRecyclerView.layoutManager = LinearLayoutManager(this)

        // Set adapter for RecyclerView
        leaderboardAdapter = LeaderboardAdapter(departmentsList)
        leaderboardRecyclerView.adapter = leaderboardAdapter


        // Load leaderboard data (This will be mock data for now)
        loadLeaderboardData()
    }

    private fun loadLeaderboardData() {
        // Sample data representing departments and their resolved complaints count
        departmentsList.add(Department("CSE", 120))
        departmentsList.add(Department("ECE-AI", 95))
        departmentsList.add(Department("ECE", 80))
        departmentsList.add(Department("CSAI", 75))
        departmentsList.add(Department("IT", 60))

        // Notify adapter to update the RecyclerView
        leaderboardAdapter.notifyDataSetChanged()
    }
}