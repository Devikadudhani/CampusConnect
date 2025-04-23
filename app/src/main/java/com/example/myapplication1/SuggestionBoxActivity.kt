package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.adapters.SuggestionsAdapter
import com.example.myapplication1.models.Suggestion
import com.example.myapplication1.models.UserInfo
import com.google.android.material.bottomnavigation.BottomNavigationView

class SuggestionBoxActivity : AppCompatActivity() {

    private val suggestions = mutableListOf<Suggestion>()
    private lateinit var adapter: SuggestionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestion_box)

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

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerSuggestions)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val user1 = UserInfo("Aanya Sharma", "21CS101", "CSE", "3rd", "aanya@example.com")
        val user2 = UserInfo("Diya Mehra", "21ME102", "Mechanical", "2nd", "diya@example.com")
        val user3 = UserInfo("Kritika Singh", "21EC103", "ECE", "4th", "kritika@example.com")
        val user4 = UserInfo("Simran Verma", "21EE104", "EEE", "2nd", "simran@example.com")
        val user5 = UserInfo("Tanya Sharma", "21BT105", "Biotech", "1st", "tanya@example.com")

        // Function to generate dummy supporters
        fun generateSupporters(count: Int): MutableList<UserInfo> {
            val supporters = mutableListOf<UserInfo>()
            repeat(count) {
                supporters.add(
                    UserInfo("Student ${it + 1}", "21XX${100 + it}", "Dept", "${(1..4).random()}th", "student${it + 1}@example.com")
                )
            }
            return supporters
        }

// Add extended suggestions
        suggestions.add(
            Suggestion(
                message = "Improve library facilities",
                postedBy = user1,
                supporters = generateSupporters(25)
            )
        )

        suggestions.add(
            Suggestion(
                message = "Extend lab hours: No weekend access",
                postedBy = user2,
                supporters = generateSupporters(18)
            )
        )

        suggestions.add(
            Suggestion(
                message = "More career counseling sessions",
                postedBy = user3,
                supporters = generateSupporters(12)
            )
        )

        suggestions.add(
            Suggestion(
                message = "Improve cafeteria food quality",
                postedBy = user4,
                supporters = generateSupporters(32)
            )
        )

        adapter = SuggestionsAdapter(suggestions)
        recyclerView.adapter = adapter
    }
}