package com.example.fourth

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etFirstNumber: EditText
    private lateinit var etSecondNumber: EditText
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvResult: TextView = findViewById(R.id.tvResult)

        findViewById<Button>(R.id.btnAdd).setOnClickListener { calculate("+") }
        findViewById<Button>(R.id.btnSubtract).setOnClickListener { calculate("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { calculate("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { calculate("/") }
    }

    private fun calculate(operator: String) {
        val num1 = etFirstNumber.text.toString().toDoubleOrNull()
        val num2 = etSecondNumber.text.toString().toDoubleOrNull()

        if (num1 == null || num2 == null) {
            tvResult.text = "Please enter valid numbers"
            return
        }

        val result = when (operator) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> {
                if (num2 == 0.0) {
                    tvResult.text = "Cannot divide by zero"
                    return
                } else {
                    num1 / num2
                }
            }
            else -> 0.0
        }

        tvResult.text = "Result: $result"
    }
}
