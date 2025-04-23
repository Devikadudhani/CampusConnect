package com.example.myapplication1

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.myapplication1.AdminProfileActivity
import com.example.myapplication1.AdminDashboardActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MoreGraphActivity : AppCompatActivity() {

    data class ComplaintData(val category: String, val count: Int)
    data class MonthlyComplaint(val month: String, val complaints: List<ComplaintData>)

    private val mockData = listOf(
        MonthlyComplaint("April 2025", listOf(
            ComplaintData("Hostel", 10),
            ComplaintData("Food", 6),
            ComplaintData("Electricity", 4),
            ComplaintData("Water Supply", 7),
            ComplaintData("Cleanliness", 5),
            ComplaintData("Internet", 8),
            ComplaintData("Security", 3),
            ComplaintData("Medical", 4)
        )),
        MonthlyComplaint("March 2025", listOf(
            ComplaintData("Hostel", 5),
            ComplaintData("Food", 7),
            ComplaintData("Electricity", 3)
        )),
        MonthlyComplaint("February 2025", listOf(
            ComplaintData("Hostel", 5),
            ComplaintData("Food", 7),
            ComplaintData("Electricity", 3),
            ComplaintData("Cleanliness", 5),
            ComplaintData("Internet", 6)
        )),

        MonthlyComplaint("January 2025", listOf(
            ComplaintData("Hostel", 12),
            ComplaintData("Food", 9),
            ComplaintData("Electricity", 5),
            ComplaintData("Medical", 5)
        ))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_graph)

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

        val container = findViewById<LinearLayout>(R.id.chartCardContainer)
        // Inside onCreate method, where you want to use the mockData
        val chartData: Map<String, List<Int>> = mockData.associate {
            it.month to it.complaints.map { complaint -> complaint.count }
        }

// Use chartData wherever needed in your chart method


        mockData.forEach { monthlyComplaint ->
            val card = layoutInflater.inflate(R.layout.month_chart_card, container, false) as CardView
            val title = card.findViewById<TextView>(R.id.monthTitle)
            val chartLayout = card.findViewById<LinearLayout>(R.id.barContainer)

            title.text = monthlyComplaint.month

            monthlyComplaint.complaints.forEach { data ->
                val label = TextView(this).apply {
                    text = "${data.category} (${data.count})"
                    textSize = 14f
                    setTextColor(Color.DKGRAY)
                }

                val bar = View(this).apply {
                    layoutParams = LinearLayout.LayoutParams(data.count * 25, 40).apply {
                        topMargin = 6
                        bottomMargin = 10
                    }
                    setBackgroundColor(Color.parseColor("#5E35B1"))
                }

                chartLayout.addView(label)
                chartLayout.addView(bar)
            }

            container.addView(card)
        }
    }
}