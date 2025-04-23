package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.random.Random

class SurveyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val resultText = findViewById<TextView>(R.id.textPollResults)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedOption = findViewById<RadioButton>(checkedId)
            val percentages = listOf(
                Random.nextInt(20, 40),
                Random.nextInt(10, 30),
                Random.nextInt(10, 30),
                Random.nextInt(5, 20)
            ).shuffled()

            val results = """
                Very satisfied: ${percentages[0]}%
                Satisfied: ${percentages[1]}%
                Dissatisfied: ${percentages[2]}%
                Very dissatisfied: ${percentages[3]}%
            """.trimIndent()

            resultText.text = "Results so far:\n\n$results"
            resultText.visibility = TextView.VISIBLE

            Toast.makeText(this, "You selected: ${selectedOption.text}", Toast.LENGTH_SHORT).show()
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.nav_polls
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_add_suggestion -> {
                    val intent = Intent(this, StudentHomeActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Add Complaint clicked", Toast.LENGTH_SHORT).show()
                    true}
                R.id.nav_add_complaint -> {
                    val intent = Intent(this, ComplaintActivity::class.java)
                    startActivity(intent)
                    startActivity(Intent(this, ComplaintActivity::class.java))
                    true
                }
                R.id.nav_track_complaint -> {
                    val intent = Intent(this, TrackComplaintActivity::class.java)
                    startActivity(intent)
                    startActivity(Intent(this, TrackComplaintActivity::class.java))
                    true
                }
                R.id.nav_polls -> {
                    val intent = Intent(this, SurveyActivity::class.java)
                    startActivity(intent)
                    startActivity(Intent(this, SurveyActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}