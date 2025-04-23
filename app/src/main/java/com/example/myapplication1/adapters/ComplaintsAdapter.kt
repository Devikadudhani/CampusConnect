package com.example.myapplication1.adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication1.models.Complaint
import com.example.myapplication1.R

class ComplaintsAdapter(private val complaints: List<Complaint>, private val isAdmin: Boolean) : RecyclerView.Adapter<ComplaintsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.complaintTitle)
        val category: TextView = itemView.findViewById(R.id.category)
        val description: TextView = view.findViewById(R.id.complaintDescription)
        val userDetails: TextView = view.findViewById(R.id.userDetails)
        val media: ImageView = view.findViewById(R.id.complaintMedia)
        val statusText: TextView = itemView.findViewById(R.id.status_text)
        val statusBar: ProgressBar = itemView.findViewById(R.id.status_bar)

        // Initialize the Spinner and the Confirm Button for assignment

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.complaint_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val complaint = complaints[position]

        // Set complaint title, description, and upvotes
        holder.title.text = complaint.title
        holder.category.text = complaint.category
        holder.description.text = complaint.description


        // Check if complaint is anonymous and whether the user is admin or not
        val user = complaint.user
        if (complaint.isAnonymous && !isAdmin) {
            holder.userDetails.text = "Anonymous User"
        } else {
            holder.userDetails.text = "${user.name}, ${user.rollNumber}, ${user.department}, ${user.year}"
        }

        // Load media if it exists
        if (complaint.media.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(complaint.media[0])
                .into(holder.media)
        }

        if (complaint.pendingPercentage < 100) {
            holder.statusBar.visibility = View.VISIBLE
            holder.statusBar.progress = complaint.pendingPercentage
            holder.statusText.text = "Status: Pending (${complaint.pendingPercentage}%)"
        } else {
            holder.statusBar.visibility = View.GONE
            holder.statusText.text = "Status: Resolved"
        }


    }

    override fun getItemCount(): Int = complaints.size

    private fun showVoterDialog(context: android.content.Context, complaint: Complaint) {
        val message = StringBuilder()

        AlertDialog.Builder(context)
            .setTitle("Voter Info")
            .setMessage(message.toString())
            .setPositiveButton("OK", null)
            .show()
    }
}