package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        // Initialize RecyclerView and set its layout manager
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set profile details
        val profileImageView = findViewById<ImageView>(R.id.profileImageView)
        val nameTextView = findViewById<TextView>(R.id.nameTextView)
        val enrollmentTextView = findViewById<TextView>(R.id.enrollmentTextView)
        val branchTextView = findViewById<TextView>(R.id.branchTextView)
        val emailTextView = findViewById<TextView>(R.id.emailTextView)
        val changePasswordButton = findViewById<Button>(R.id.changePasswordButton)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        // Set example details (replace with actual user data if you store it)
        profileImageView.setImageResource(R.drawable.img_2)  // Replace with actual image
        nameTextView.text = "Isha"
        enrollmentTextView.text = "07501012024"
        branchTextView.text = "CSE"

        // Display logged-in user's email
        emailTextView.text = currentUser?.email ?: "No Email Found"

        // Change Password action → navigate to forgot password page
        changePasswordButton.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        // Logout action
        logoutButton.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, ExistingUserLoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
        }

        // Set up Bottom Navigation Bar
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_add_suggestion -> {
                    val intent = Intent(this, StudentHomeActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Returned to Home", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_add_complaint -> {
                    val intent = Intent(this, ComplaintActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Add Complaint clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_track_complaint -> {
                    val intent = Intent(this, TrackComplaintActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Track Complaint clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_polls -> {
                    val intent = Intent(this, SurveyActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Polls clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }
    }
}
