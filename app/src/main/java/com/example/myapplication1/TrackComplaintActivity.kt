package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.widget.ProgressBar
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView

class TrackComplaintActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_complaint)

        val complaints = listOf(
            Complaint("Network issue in the lab-106", "Solved"),
            Complaint("fan issue in room E-212", "Solved"),
            Complaint("extra chairs in library", "Solved"),
            Complaint("Water leakage in washroom", "Pending", 30)
        )

        val progressView = findViewById<UShapeProgressView>(R.id.progressView)
        progressView.total = complaints.size
        progressView.solved = complaints.count { it.status == "Solved" }
        progressView.pending = complaints.count { it.status == "Pending" }

// Call the animation method (this should work now)
        progressView.animateProgress()


        // RecyclerView setup
        val recyclerView = findViewById<RecyclerView>(R.id.complaintRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ComplaintAdapter(complaints)

        // Total complaints text
        val totalComplaints = findViewById<TextView>(R.id.totalComplaintsCount)
        totalComplaints.text = complaints.size.toString()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.nav_track_complaint
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_add_suggestion -> {
                    val intent = Intent(this, StudentHomeActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Add Complaint clicked", Toast.LENGTH_SHORT).show()
                    true}
                R.id.nav_add_complaint -> {
                    val intent = Intent(this, ComplaintActivity::class.java)
                    startActivity(intent)
                    startActivity(Intent(this, ComplaintActivity::class.java))
                    true
                }
                R.id.nav_track_complaint -> {
                    val intent = Intent(this, TrackComplaintActivity::class.java)
                    startActivity(intent)
                    startActivity(Intent(this, TrackComplaintActivity::class.java))
                    true
                }
                R.id.nav_polls -> {
                    val intent = Intent(this, SurveyActivity::class.java)
                    startActivity(intent)
                    startActivity(Intent(this, SurveyActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }


    // Data class for complaints
    data class Complaint(
        val title: String,
        val status: String,
        val progress: Int = 0
    )

    // Adapter for RecyclerView
    class ComplaintAdapter(private val complaints: List<Complaint>) :
        RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder>() {

        class ComplaintViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(R.id.complaintTitle)
            val status: TextView = view.findViewById(R.id.complaintStatus)
            val progressLayout: View = view.findViewById(R.id.progressLayout)
            val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
            val progressText: TextView = view.findViewById(R.id.progressText)
        }


        override fun onCreateViewHolder(
            parent: android.view.ViewGroup,
            viewType: Int
        ): ComplaintViewHolder {
            val view = android.view.LayoutInflater.from(parent.context)
                .inflate(R.layout.item_complaint, parent, false)
            return ComplaintViewHolder(view)
        }

        override fun onBindViewHolder(holder: ComplaintViewHolder, position: Int) {
            val complaint = complaints[position]
            holder.title.text = complaint.title
            holder.status.text = complaint.status

            if (complaint.status == "Solved") {
                holder.status.setTextColor(android.graphics.Color.parseColor("#4CAF50")) // Green
                holder.progressLayout.visibility = View.GONE
            } else {
                holder.status.setTextColor(android.graphics.Color.parseColor("#F44336")) // Red
                holder.progressLayout.visibility = View.VISIBLE
                holder.progressBar.progress = complaint.progress
                holder.progressText.text = "Progress: ${complaint.progress}%"
            }
        }

        override fun getItemCount() = complaints.size
    }
}