package com.example.myapplication1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var etResetEmail: EditText
    private lateinit var btnResetPassword: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        etResetEmail = findViewById(R.id.etResetEmail)
        btnResetPassword = findViewById(R.id.btnResetPassword)
        auth = FirebaseAuth.getInstance()

        btnResetPassword.setOnClickListener {
            val email = etResetEmail.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            } else {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Password reset email sent. Check your inbox.", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }
}
