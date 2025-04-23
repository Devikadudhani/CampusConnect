package com.example.first

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log

class StudentLoginActivity : AppCompatActivity() {


    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var enrollInput: EditText
    private lateinit var yearSpinner: Spinner
    private lateinit var branchSpinner: Spinner
    private lateinit var loginBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_login)


        // Initialize views
        nameInput = findViewById(R.id.nameInput)
        emailInput = findViewById(R.id.emailInput)
        enrollInput = findViewById(R.id.enrollInput)
        yearSpinner = findViewById(R.id.yearSpinner)
        branchSpinner = findViewById(R.id.branchSpinner)
        loginBtn = findViewById(R.id.loginButton)

        setupSpinners()
        setupLoginButton()
        val alreadyHaveAccountText = findViewById<TextView>(R.id.alreadyHaveAccountText)
        alreadyHaveAccountText.setOnClickListener {
            val intent = Intent(this, ExistingUserLoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setupSpinners() {
        val years = listOf("Select Batch Year", "2025", "2026", "2027", "2028", "2029")
        val yearAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        yearSpinner.adapter = yearAdapter

        val branches =
            listOf("Select Branch", "CSE", "CSE-AI", "ECE", "ECE-AI", "AI/ML", "IT", "OTHERS")
        val branchAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, branches).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        branchSpinner.adapter = branchAdapter
    }

    private fun setupLoginButton() {
        loginBtn.setOnClickListener {
            Log.d("StudentLogin", "Login button clicked")
            if (validateInput()) {
                showToast("Account created successfully")
                startActivity(Intent(this, CreatePasswordActivity::class.java))
                finish()
            }
        }
    }

    private fun validateInput(): Boolean {
        val email = emailInput.text.toString().trim()
        val enroll = enrollInput.text.toString().trim()
        val name = nameInput.text.toString().trim()
        val year = yearSpinner.selectedItem.toString()
        val branch = branchSpinner.selectedItem.toString()

        return when {
            !email.endsWith("@igdtuw.ac.in") -> {
                showToast("Use IGDTUW email only")
                false
            }

            enroll.length != 11 -> {
                showToast("Enrollment number must be 11 digits")
                false
            }

            name.isEmpty() -> {
                showToast("Name is required")
                false
            }

            year == "Select Batch Year" -> {
                showToast("Please select a valid batch year")
                false
            }

            branch == "Select Branch" -> {
                showToast("Please select a valid branch")
                false
            }

            else -> true
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}