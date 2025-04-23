package com.example.myapplication1


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.myapplication1.models.UserDetails
import com.example.myapplication1.AdminProfileActivity
import com.example.myapplication1.AdminDashboardActivity

class UserDetailsActivity : AppCompatActivity() {

    private lateinit var userDetailsContainer: LinearLayout
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    // Avoid reopening the same activity if already on dashboard
                    if (this !is AdminDashboardActivity) {
                        val intent = Intent(this, AdminDashboardActivity::class.java)
                        startActivity(intent)
                        finish() // optional: to avoid backstack issues
                    }
                    true
                }

                R.id.nav_profile -> {
                    if (this !is AdminProfileActivity) {
                        val intent = Intent(this, AdminProfileActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    true
                }

                else -> false
            }
        }

        userDetailsContainer = findViewById(R.id.userDetailsContainer)
        searchView = findViewById(R.id.searchView)

        val userList = listOf(
            UserDetails("Aanya Sharma", "21BCS001", "Computer Science", 3, "aanya@example.com"),
            UserDetails("Riya Mehta", "21ECE015", "Electronics", 2, "riya@example.com"),
            UserDetails("Sneha Roy", "22ME020", "Mechanical", 1, "sneha@example.com"),
            UserDetails("Anushka Rao", "22ENG016", "English Literature", 1, "anushka@example.com"),
            UserDetails("Ishita Singh", "20CSE102", "Computer Science", 4, "ishita@example.com"),
            UserDetails("Nikita Jain", "21IT034", "Information Technology", 3, "nikita@example.com"),
            UserDetails("Tanvi Verma", "22ECE018", "Electronics", 2, "tanvi@example.com"),
            UserDetails("Divya Patel", "23ME045", "Mechanical", 1, "divya@example.com"),
            UserDetails("Kritika Malhotra", "20CIV007", "Civil", 4, "kritika@example.com"),
            UserDetails("Sanya Kapoor", "21BT003", "Biotech", 3, "sanya@example.com"),
            UserDetails("Pooja Desai", "23CHE012", "Chemical", 1, "pooja@example.com"),
            UserDetails("Harshita Nair", "22ECE027", "Electronics", 2, "harshita@example.com"),
            UserDetails("Aarushi Rana", "21CSE021", "Computer Science", 3, "aarushi@example.com"),
            UserDetails("Priya Joshi", "20EEE030", "Electrical", 4, "priya@example.com"),
            UserDetails("Bhavya Taneja", "23PHY005", "Physics", 1, "bhavya@example.com"),
            UserDetails("Ira Bhattacharya", "22MAT011", "Mathematics", 2, "ira@example.com"),
            UserDetails("Shreya Ghosh", "20ENG009", "English", 4, "shreya@example.com"),
            UserDetails("Neha Kulkarni", "21CSE045", "Computer Science", 3, "neha@example.com"),
            UserDetails("Simran Kaur", "23BIO022", "Biology", 1, "simran@example.com"),
            UserDetails("Megha Sharma", "22IT037", "Information Technology", 2, "megha@example.com"),
            UserDetails("Avni Deshmukh", "21ECE042", "Electronics", 3, "avni@example.com"),
            UserDetails("Ritika Sood", "20CHE014", "Chemical", 4, "ritika@example.com"),
            UserDetails("Shraddha Menon", "23ME039", "Mechanical", 1, "shraddha@example.com"),
            UserDetails("Tanya Arora", "21BT026", "Biotech", 3, "tanya@example.com"),
            UserDetails("Muskan Ali", "22CIV017", "Civil", 2, "muskan@example.com")
        )

        fun displayUsers(filteredList: List<UserDetails>) {
            userDetailsContainer.removeAllViews()
            for (user in filteredList) {
                val cardView = CardView(this).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(0, 0, 0, 24)
                    }
                    radius = 20f
                    cardElevation = 8f
                    setContentPadding(32, 32, 32, 32)
                    setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                }

                val infoLayout = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }

                val nameText = createStyledTextView("Name: ${user.name}")
                val rollText = createStyledTextView("Roll Number: ${user.rollNumber}")
                val deptText = createStyledTextView("Department: ${user.department}")
                val yearText = createStyledTextView("Year: ${user.year}")
                val emailText = createStyledTextView("Email: ${user.email}")

                infoLayout.apply {
                    addView(nameText)
                    addView(rollText)
                    addView(deptText)
                    addView(yearText)
                    addView(emailText)
                }

                cardView.addView(infoLayout)
                userDetailsContainer.addView(cardView)
            }
        }

        displayUsers(userList)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = userList.filter {
                    it.name.contains(newText ?: "", ignoreCase = true)
                }
                displayUsers(filteredList)
                return true
            }
        })
    }

    private fun createStyledTextView(text: String): TextView {
        return TextView(this).apply {
            this.text = text
            textSize = 16f
            setTextColor(Color.DKGRAY)
            setPadding(0, 8, 0, 8)
        }
    }
}