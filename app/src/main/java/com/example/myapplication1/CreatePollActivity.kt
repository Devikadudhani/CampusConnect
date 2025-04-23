package com.example.myapplication1

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.example.myapplication1.models.Poll
import com.google.android.material.bottomnavigation.BottomNavigationView

class CreatePollActivity : AppCompatActivity() {

    private lateinit var questionEditText: EditText
    private lateinit var optionsContainer: LinearLayout
    private lateinit var addOptionButton: Button
    private lateinit var createButton: Button
    private var optionCount = 0 // Track the number of options added
    private lateinit var pollsList: MutableList<Poll> // This should ideally be replaced by your DB logic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_poll)

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

        questionEditText = findViewById(R.id.pollQuestion)
        optionsContainer = findViewById(R.id.optionsContainer)
        addOptionButton = findViewById(R.id.btnAddOption)
        createButton = findViewById(R.id.createPollButton)

        pollsList = mutableListOf() // Simulated storage

        // Add initial four options dynamically
        for (i in 1..4) {
            addOption("Option $i", false) // First 4 options don't have the cross
        }

        // Add new option when '+' button is clicked
        addOptionButton.setOnClickListener {
            addOption("Option ${optionCount + 1}", true) // New options will have the cross
        }

        // Create Poll logic
        createButton.setOnClickListener {
            val question = questionEditText.text.toString().trim()

            // Gather all options from the dynamically created EditText fields
            val options = mutableListOf<String>()
            for (i in 0 until optionsContainer.childCount) {
                val optionLayout = optionsContainer.getChildAt(i) as LinearLayout
                val optionEditText = optionLayout.getChildAt(0) as EditText
                val option = optionEditText.text.toString().trim()
                if (option.isNotBlank()) {
                    options.add(option)
                }
            }

            // Ensure that there's at least one option and question is filled
            if (question.isNotBlank() && options.isNotEmpty()) {
                val poll = Poll(question, options, mutableListOf())
                pollsList.add(poll)
                Toast.makeText(this, "Poll Created!", Toast.LENGTH_SHORT).show()
                finish() // Go back after creating the poll
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to add a new option dynamically
    private fun addOption(optionText: String, showRemoveButton: Boolean) {
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.HORIZONTAL

        val optionEditText = EditText(this)
        optionEditText.layoutParams = LinearLayout.LayoutParams(
            0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
        )
        optionEditText.hint = optionText

        linearLayout.addView(optionEditText)

        // Add remove button for options after the first 4
        if (showRemoveButton) {
            val removeButton = ImageButton(this)
            removeButton.layoutParams = LinearLayout.LayoutParams(
                resources.getDimensionPixelSize(R.dimen.cross_button_size), // Set the size from dimens
                resources.getDimensionPixelSize(R.dimen.cross_button_size)
            )
            removeButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel)
            removeButton.setOnClickListener {
                optionsContainer.removeView(linearLayout)
                optionCount-- // Decrease the option count after removal
            }
            linearLayout.addView(removeButton)
        }

        optionsContainer.addView(linearLayout)
        optionCount++ // Increment the option count
    }
}