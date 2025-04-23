package com.example.myapplication1.adapters


import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.models.Poll
import com.example.myapplication1.R

class PollsAdapter(
    private val polls: MutableList<Poll>, // ✅ Changed from Polls to Poll
    private val isAdmin: Boolean
) : RecyclerView.Adapter<PollsAdapter.PollViewHolder>() {

    class PollViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pollQuestion: TextView = view.findViewById(R.id.pollQuestion)
        val pollOptions: TextView = view.findViewById(R.id.pollOptions)
        val viewVotersButton: Button = view.findViewById(R.id.viewVotersButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PollViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_poll, parent, false)
        return PollViewHolder(view)
    }

    override fun onBindViewHolder(holder: PollViewHolder, position: Int) {
        val poll = polls[position]
        holder.pollQuestion.text = poll.question
        holder.pollOptions.text = poll.options.joinToString(", ")

        if (isAdmin) {
            holder.viewVotersButton.visibility = View.VISIBLE
            holder.viewVotersButton.setOnClickListener {
                val voters = poll.votes.joinToString("\n") {
                    "${it.voterName} (${it.voterRoll}) voted: ${it.selectedOption}"
                }

                AlertDialog.Builder(holder.itemView.context)
                    .setTitle("Voters for this poll")
                    .setMessage(voters)
                    .setPositiveButton("OK", null)
                    .show()
            }
        } else {
            holder.viewVotersButton.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = polls.size
}