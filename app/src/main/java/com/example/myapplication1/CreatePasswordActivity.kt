package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class CreatePasswordActivity : AppCompatActivity() {

    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var createAccountButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_password)

        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        createAccountButton = findViewById(R.id.createAccountButton)
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser ?: return

        createAccountButton.setOnClickListener {
            val password = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            if (password.isEmpty() || confirmPassword.isEmpty()) {
                showToast("Please fill in both fields")
            } else if (password != confirmPassword) {
                showToast("Passwords do not match")
            } else {
                currentUser.updatePassword(password)
                    .addOnSuccessListener {
                        showToast("Account created successfully")
                        startActivity(Intent(this, StudentHomeActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener {
                        showToast("Failed to set password: ${it.message}")
                    }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
