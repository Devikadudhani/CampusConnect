package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.myapplication1.SuggestionAdapter

class StudentHomeActivity : AppCompatActivity() {
    private lateinit var suggestionAdapter: SuggestionAdapter
    private lateinit var suggestions: MutableList<Suggestion>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home)

        val suggestionsRecyclerView = findViewById<RecyclerView>(R.id.suggestionsRecyclerView)
        suggestionsRecyclerView.layoutManager = LinearLayoutManager(this)

        suggestions = mutableListOf(
            Suggestion("Improve library facilities", 24),
            Suggestion("Extend lab hours: No weekend access", 18),
            Suggestion("More career counseling sessions", 12),
            Suggestion("Improve cafeteria food quality", 8)
        )

        suggestionAdapter = SuggestionAdapter(suggestions)
        suggestionsRecyclerView.adapter = suggestionAdapter

        val suggestionInput = findViewById<EditText>(R.id.newSuggestionEditText)
        val postButton = findViewById<Button>(R.id.submitSuggestionButton)

        postButton.setOnClickListener {
            val newText = suggestionInput.text.toString().trim()
            if (newText.isNotEmpty()) {
                val newSuggestion = Suggestion(newText, 0)
                suggestions.add(0, newSuggestion)
                suggestionAdapter.notifyItemInserted(0)
                suggestionsRecyclerView.scrollToPosition(0)
                suggestionInput.text.clear()
                Toast.makeText(this, "Suggestion posted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Enter a suggestion", Toast.LENGTH_SHORT).show()
            }
        }
        val profileButton = findViewById<ImageButton>(R.id.profileButton)
        profileButton.setOnClickListener {
            // Navigate to ProfileActivity when the profile button is clicked
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_add_suggestion -> {
                    suggestionsRecyclerView.scrollToPosition(0)
                    Toast.makeText(this, "Returned to Home", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_add_complaint -> {
                    Toast.makeText(this, "Add Complaint clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_track_complaint -> {
                    val intent = Intent(this, TrackComplaintActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Track Complaint clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_polls -> {
                    Toast.makeText(this, "Polls clicked", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}

// Keep Suggestion data class here or in its own file
data class Suggestion(var text: String, var supporters: Int)
