package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseUser

class AdminSignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_signup)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        progressBar = findViewById(R.id.progressBar)

        val etName = findViewById<EditText>(R.id.etName)
        val etDepartment = findViewById<EditText>(R.id.etDepartment)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)

        btnSignUp.setOnClickListener {
            val name = etName.text.toString().trim()
            val department = etDepartment.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            if (validateInputs(name, department, email, password, confirmPassword)) {
                registerAdmin(name, department, email, password)
            }
        }

        tvLogin.setOnClickListener {
            startActivity(Intent(this, AdminLoginActivity::class.java))
            finish()
        }
    }

    private fun validateInputs(
        name: String,
        department: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (name.isEmpty()) {
            showToast("Please enter your name")
            return false
        }

        if (department.isEmpty()) {
            showToast("Please enter your department")
            return false
        }

        if (email.isEmpty()) {
            showToast("Please enter your email")
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Please enter a valid email")
            return false
        }

        if (password.isEmpty()) {
            showToast("Please enter password")
            return false
        }

        if (password.length < 6) {
            showToast("Password must be at least 6 characters")
            return false
        }

        if (password != confirmPassword) {
            showToast("Passwords do not match")
            return false
        }

        return true
    }

    private fun registerAdmin(name: String, department: String, email: String, password: String) {
        showProgress(true)

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Update user profile with name
                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()

                    user?.updateProfile(profileUpdates)?.addOnCompleteListener { profileTask ->
                        if (profileTask.isSuccessful) {
                            // Save additional user data to Firestore
                            val adminData = hashMapOf(
                                "name" to name,
                                "department" to department,
                                "email" to email,
                                "createdAt" to System.currentTimeMillis()
                            )

                            db.collection("admins").document(user.uid)
                                .set(adminData)
                                .addOnSuccessListener {
                                    // Send email verification
                                    sendEmailVerification(user)
                                }
                                .addOnFailureListener { e ->
                                    showProgress(false)
                                    showToast("Failed to save user data: ${e.message}")
                                }
                        } else {
                            showProgress(false)
                            showToast("Failed to update profile: ${profileTask.exception?.message}")
                        }
                    }
                } else {
                    showProgress(false)
                    showToast("Registration failed: ${task.exception?.message}")
                }
            }
    }



    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification()
            .addOnCompleteListener { task ->
                showProgress(false)
                if (task.isSuccessful) {
                    showToast("Verification email sent to ${user.email}")
                    auth.signOut() // Force user to verify email before login
                    startActivity(Intent(this, AdminLoginActivity::class.java))
                    finish()
                } else {
                    showToast("Failed to send verification email: ${task.exception?.message}")
                }
            }
    }

    private fun showProgress(show: Boolean) {
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        findViewById<Button>(R.id.btnSignUp).isEnabled = !show
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}