package com.example.myapplication1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.models.Department
import com.example.myapplication1.R

class LeaderboardAdapter(private val departmentsList: List<Department>) :
    RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_leaderboard, parent, false)
        return LeaderboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {
        val department = departmentsList[position]
        holder.rankTextView.text = (position + 1).toString()
        holder.departmentName.text = department.name
        holder.resolvedComplaintsCount.text = department.resolvedComplaints.toString()
    }

    override fun getItemCount(): Int {
        return departmentsList.size
    }

    class LeaderboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rankTextView: TextView = itemView.findViewById(R.id.rankTextView)
        val departmentName: TextView = itemView.findViewById(R.id.departmentName)
        val resolvedComplaintsCount: TextView = itemView.findViewById(R.id.resolvedComplaintsCount)
    }
}