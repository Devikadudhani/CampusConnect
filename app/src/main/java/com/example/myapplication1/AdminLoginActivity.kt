package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AdminLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val cbRememberMe = findViewById<CheckBox>(R.id.cbRememberMe)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)
        val tvSignUp = findViewById<TextView>(R.id.tvSignUp)

        // Login Button Click
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                val auth = com.google.firebase.auth.FirebaseAuth.getInstance()

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            if (user != null && user.isEmailVerified) {
                                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, AdminDashboardActivity::class.java))
                                finish()
                            } else {
                                auth.signOut()
                                Toast.makeText(this, "Please verify your email first. Check your inbox.", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }


        // Forgot Password Click
        tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        // Sign Up Click
        tvSignUp.setOnClickListener {
            startActivity(Intent(this, AdminSignUpActivity::class.java))
        }
    }
}