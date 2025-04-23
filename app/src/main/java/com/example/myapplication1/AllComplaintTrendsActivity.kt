package com.example.myapplication1

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import android.content.Intent
import com.example.myapplication1.BarChartView
import com.example.myapplication1.AdminProfileActivity
import com.example.myapplication1.AdminDashboardActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class AllComplaintTrendsActivity : AppCompatActivity() {

    private lateinit var trendsContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_complaint_trends)

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

        trendsContainer = findViewById(R.id.trendsContainer)

        // Monthly Data: Map<String, List<Int>> format
        val monthlyData: Map<String, List<Int>> = mapOf(
            "April 2025" to listOf(10, 20, 15, 30),
            "March 2025" to listOf(5, 12, 8, 22),
            "February 2025" to listOf(7, 9, 13, 18),
            "January 2025" to listOf(6, 10, 14, 20)
        )

        // Iterate over each month and display the chart
        for ((month, data) in monthlyData) {
            val cardView = CardView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 16, 0, 16)
                }
                radius = 16f
                cardElevation = 8f
                setContentPadding(16, 16, 16, 16)
            }

            val monthText = TextView(this).apply {
                text = month
                textSize = 18f
                setTextColor(resources.getColor(android.R.color.black))
                setPadding(0, 0, 0, 16)
            }

            // Correctly call setChartData here with Map<String, List<Int>> data
            val barChartView = BarChartView(this, null)
            barChartView.setChartData(monthlyData)  // Corrected method call

            val layout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                addView(monthText)
                addView(barChartView)
            }

            cardView.addView(layout)
            trendsContainer.addView(cardView)
        }
    }
}