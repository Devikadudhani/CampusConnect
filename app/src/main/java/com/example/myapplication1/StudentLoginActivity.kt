package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class StudentLoginActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var enrollInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var yearSpinner: Spinner
    private lateinit var branchSpinner: Spinner
    private lateinit var loginBtn: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_login)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        nameInput = findViewById(R.id.nameInput)
        emailInput = findViewById(R.id.emailInput)
        enrollInput = findViewById(R.id.enrollInput)
        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        yearSpinner = findViewById(R.id.yearSpinner)
        branchSpinner = findViewById(R.id.branchSpinner)
        loginBtn = findViewById(R.id.loginButton)
        progressBar = findViewById(R.id.progressBar)

        setupSpinners()
        setupLoginButton()

        findViewById<TextView>(R.id.alreadyHaveAccountText).setOnClickListener {
            startActivity(Intent(this, ExistingUserLoginActivity::class.java))
        }
    }

    private fun setupSpinners() {
        val years = listOf("Select Batch Year", "2025", "2026", "2027", "2028", "2029")
        val branches = listOf("Select Branch", "CSE", "CSE-AI", "ECE", "ECE-AI", "AI/ML", "IT", "OTHERS")

        yearSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        branchSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, branches).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

    private fun setupLoginButton() {
        loginBtn.setOnClickListener {
            if (validateInput()) {
                registerStudent()
            }
        }
    }

    private fun validateInput(): Boolean {
        val email = emailInput.text.toString().trim()
        val enroll = enrollInput.text.toString().trim()
        val name = nameInput.text.toString().trim()
        val year = yearSpinner.selectedItem.toString()
        val branch = branchSpinner.selectedItem.toString()
        val password = passwordInput.text.toString()
        val confirmPassword = confirmPasswordInput.text.toString()

        return when {
            !email.endsWith("@igdtuw.ac.in") || !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                showToast("Use a valid IGDTUW email")
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
            password.length < 6 -> {
                showToast("Password must be at least 6 characters")
                false
            }
            password != confirmPassword -> {
                showToast("Passwords do not match")
                false
            }
            else -> true
        }
    }

    private fun registerStudent() {
        showProgress(true)

        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()
        val name = nameInput.text.toString().trim()
        val enroll = enrollInput.text.toString().trim()
        val year = yearSpinner.selectedItem.toString()
        val branch = branchSpinner.selectedItem.toString()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()

                    user?.updateProfile(profileUpdates)?.addOnCompleteListener {
                        val studentData = hashMapOf(
                            "name" to name,
                            "enrollment" to enroll,
                            "batchYear" to year,
                            "branch" to branch,
                            "email" to email,
                            "createdAt" to System.currentTimeMillis()
                        )

                        db.collection("students").document(user.uid)
                            .set(studentData)
                            .addOnSuccessListener {
                                showProgress(false)
                                showToast("Account created successfully")
                                startActivity(Intent(this, StudentHomeActivity::class.java))
                                finish()
                            }
                            .addOnFailureListener { e ->
                                showProgress(false)
                                showToast("Failed to save data: ${e.message}")
                            }
                    }
                } else {
                    showProgress(false)
                    showToast("Registration failed: ${task.exception?.message}")
                }
            }
    }

    private fun showProgress(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        loginBtn.isEnabled = !show
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
