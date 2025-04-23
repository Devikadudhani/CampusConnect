package com.example.myapplication1


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.EditText // Import this
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.adapters.ComplaintsAdapter
import com.example.myapplication1.models.Complaint
import com.example.myapplication1.models.Department
import com.example.myapplication1.models.Feedback
import com.example.myapplication1.models.User
// Import BarChartView class
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var complaintsRecyclerView: RecyclerView
    private lateinit var complaintsAdapter: ComplaintsAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavBar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    // Already on dashboard
                    true
                }
                R.id.nav_profile -> {
                    val intent = Intent(this, AdminProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }

        // Add this variable to track if the user is admin
        val isAdmin = true  // Change this based on your logic (true for admin, false for subordinate)

        // Initialize RecyclerView
        complaintsRecyclerView = findViewById(R.id.complaintsRecyclerView)

        // Set up the layout manager
        complaintsRecyclerView.layoutManager = LinearLayoutManager(this)

        // Now, create the adapter with the isAdmin value
        complaintsAdapter = ComplaintsAdapter(getComplaintsList(), isAdmin)

        // Set the adapter to the RecyclerView
        complaintsRecyclerView.adapter = complaintsAdapter

        val btnExistingUsers = findViewById<Button>(R.id.btnExistingUsers)
        btnExistingUsers.setOnClickListener {
            val intent = Intent(this, UserDetailsActivity::class.java)
            startActivity(intent)
        }

        val feedbackButton = findViewById<Button>(R.id.feedbackButton)
        feedbackButton.setOnClickListener {
            showFeedbackReports()
        }

        // Set up leaderboard
        val leaderboardButton = findViewById<Button>(R.id.leaderboardButton)
        leaderboardButton.setOnClickListener {
            showLeaderboard()
        }

        // Set up notifications
        val notificationButton = findViewById<Button>(R.id.notificationsButton)
        notificationButton.setOnClickListener {
            showNotifications()
        }

        // Set up polls/feedback system
        val pollsButton = findViewById<Button>(R.id.pollsButton)
        pollsButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Poll Management Options")

            val pollOptions = arrayOf(
                "Create Poll",
                "View Active Polls",
                "Past Polls"
            )

            builder.setItems(pollOptions) { _, which ->
                when (which) {
                    0 -> startActivity(Intent(this, CreatePollActivity::class.java))
                    1 -> Toast.makeText(this, "Opening Active Polls...", Toast.LENGTH_SHORT).show()
                    2 -> Toast.makeText(this, "Opening Poll Results...", Toast.LENGTH_SHORT).show()
                    3 -> Toast.makeText(this, "Opening Past Polls...", Toast.LENGTH_SHORT).show()
                    4 -> Toast.makeText(this, "Opening Delete Poll Option...", Toast.LENGTH_SHORT).show()
                }
            }

            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

            builder.show()
        }

        // Set up anonymous suggestions
        val suggestionBoxButton = findViewById<Button>(R.id.suggestionBoxButton)
        suggestionBoxButton.setOnClickListener {
            openSuggestionBox()
        }

        // Initialize the bar chart
        val barChartView = findViewById<BarChartView>(R.id.complaintsBarChart)

        val chartData: Map<String, List<Int>> = mapOf(
            "Hostel" to listOf(10),
            "Food" to listOf(6),
            "Electricity" to listOf(4)
        )

        barChartView.setChartData(chartData)




// Set up the "View More" button
        val viewMoreButton = findViewById<TextView>(R.id.viewMoreButton)
        viewMoreButton.setOnClickListener {
            Toast.makeText(this, "Opening detailed graph...", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MoreGraphActivity::class.java)
            startActivity(intent)
        }


    }


    // ✅ Full Dummy Data
    private fun getComplaintsList(): List<Complaint> {
        val user1 = User("Aanya Sharma", "21CS001", "Computer Science", 3)
        val user2 = User("Diya Mehra", "21EC002", "Electronics", 2)
        val user3 = User("Kritika Singh", "21ME003", "Mechanical", 1)

        return listOf(
            Complaint(
                title = "AC installation",
                category = "Infrastructure",
                description = "It has become quite hot these days but ACs are present in limited classrooms only",
                user = user1,
                media = listOf("https://via.placeholder.com/150"),
            ),
            Complaint(
                title = "Wi-Fi Not Working",
                category = "Infrastructure",
                description = "Wi-Fi has been down for 3 days and it has become difficult to work.",
                user = user2,
                media = listOf(),
            ),
            Complaint(
                title = "Fan Broken in Classroom",
                category = "Classroom Maintenance",
                description = "The fan in Room ECE-302 is not working and it's too hot to sit.",
                user = user3,
                media = listOf(),
            )
        )
    }

    // Placeholder methods for other features

    private fun showNotifications() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Send Notification")

        // Input field for notification message
        val input = EditText(this)
        input.hint = "Enter your notification message"
        builder.setView(input)

        builder.setPositiveButton("Send") { dialog, _ ->
            val notificationMessage = input.text.toString()
            if (notificationMessage.isNotEmpty()) {
                Toast.makeText(this, "Notification Sent: $notificationMessage", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    private fun openSuggestionBox() {
        val intent = Intent(this, SuggestionBoxActivity::class.java)
        startActivity(intent)
    }


    // Sample method to show leaderboard
    private fun showLeaderboard() {
        // This will calculate the department with the most complaints
        val departmentComplaintCount = mutableMapOf<String, Int>()

        // Sample logic: Count complaints per department
        getComplaintsList().forEach { complaint ->
            val department = complaint.user.department
            departmentComplaintCount[department] = departmentComplaintCount.getOrDefault(department, 0) + 1
        }

        // Prepare data to pass to LeaderboardActivity
        val leaderboardData = departmentComplaintCount.entries.map {
            Department(it.key, it.value)
        }

        // Pass the data to LeaderboardActivity
        val intent = Intent(this, LeaderboardActivity::class.java)
        intent.putParcelableArrayListExtra("leaderboardData", ArrayList(leaderboardData))
        startActivity(intent)
    }


    // Sample method to show active polls/feedback
    private fun showPolls() {
        val polls = listOf(
            "Should the Wi-Fi speed be increased?",
            "Is the mess food quality satisfactory?",
            "Do you want longer library hours?"
        )

        // Display polls as Toast (You can later replace this with a RecyclerView to display a list)
        val pollsList = polls.joinToString("\n") { it }
        Toast.makeText(this, "Active Polls:\n$pollsList", Toast.LENGTH_LONG).show()
    }

    private fun getComplaintCategoryCount(): Map<String, Int> {
        val categoryMap = mutableMapOf<String, Int>()

        // Iterate over the complaints and categorize them by type
        for (complaint in getComplaintsList()) {
            categoryMap[complaint.category] = categoryMap.getOrDefault(complaint.category, 0) + 1
        }

        return categoryMap
    }

    private fun showFeedbackReports() {
        val feedbackList = listOf(
            Feedback("The complaint system is very smooth and easy to use.", 5, "Aanya Sharma", "April 20, 2025"),
            Feedback("It would be great if complaints are resolved faster.", 3, "Diya Mehra", "April 18, 2025"),
            Feedback("App UI is friendly, but I faced a few crashes.", 4, "Kritika Singh", "April 15, 2025"),
            Feedback("Anonymous feedback option is helpful.", 5, "Tanya Sharma", "April 12, 2025"),
            Feedback("Poll results are transparent and well shown.", 4, "Simran Verma", "April 10, 2025"),

            // New feedbacks
            Feedback("Sometimes the app logs me out randomly.", 2, "Neha Yadav", "April 8, 2025"),
            Feedback("Too many notifications can get annoying.", 2, "Riya Kapoor", "April 7, 2025"),
            Feedback("Really like the clean design and layout.", 5, "Ishita Malhotra", "April 5, 2025"),
            Feedback("Resolved complaints take time to disappear from the list.", 3, "Pooja Sinha", "April 4, 2025"),
            Feedback("The new poll system is a good initiative.", 4, "Sana Kaur", "April 2, 2025"),
            Feedback("Faced issues uploading images with complaints.", 2, "Shreya Dutt", "March 30, 2025"),
            Feedback("Helpful app, quick responses by admin team!", 5, "Anjali Chauhan", "March 28, 2025"),
            Feedback("Search filter is missing, it's hard to find specific complaints.", 3, "Garima Joshi", "March 26, 2025"),
            Feedback("Too many categories make it confusing sometimes.", 2, "Rashmi Jain", "March 25, 2025"),
            Feedback("Love how my feedback was acknowledged within a day!", 5, "Nikita Bansal", "March 22, 2025"),
            Feedback("Would be better if we can comment on complaints too.", 4, "Meera Vaidya", "March 20, 2025")
        )
        // No comma after the last item


        val feedbackText = feedbackList.joinToString("\n\n") { feedback ->
            "By ${feedback.userName} (${feedback.timestamp}):\n${feedback.message} ${"⭐".repeat(feedback.rating)}"
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("User Feedback")
        builder.setMessage(feedbackText)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }
}