package com.example.myapplication1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val nameInput: EditText = findViewById(R.id.editName)
        val positionInput: EditText = findViewById(R.id.editPosition)
        val emailInput: EditText = findViewById(R.id.editEmail)
        val continueBtn: Button = findViewById(R.id.btnContinue)

        continueBtn.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val position = positionInput.text.toString().trim()
            val email = emailInput.text.toString().trim()

            if (name.isEmpty() || position.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // You can replace this with navigation or data storage
                Toast.makeText(this, "Welcome, $name!", Toast.LENGTH_LONG).show()
            }
        }
    }
}
