package com.example.first

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.first.R

class SuggestionAdapter(private val suggestions: MutableList<Suggestion>) :
    RecyclerView.Adapter<SuggestionAdapter.SuggestionViewHolder>() {

    class SuggestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val suggestionText: TextView = view.findViewById(R.id.suggestionText)
        val supportersText: TextView = view.findViewById(R.id.supportersText)
        val supportButton: Button = view.findViewById(R.id.supportButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_suggestion, parent, false)
        return SuggestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        val suggestion = suggestions[position]
        holder.suggestionText.text = suggestion.text
        holder.supportersText.text = "${suggestion.supporters} supporters"
        holder.supportButton.text = "+"

        // Highlight the suggestion with the highest votes (top one in sorted list)
        if (position == 0) {
            holder.itemView.setBackgroundColor(android.graphics.Color.parseColor("#FFEB3B"))  // Yellow background for top suggestion
            holder.suggestionText.setTextColor(android.graphics.Color.BLACK)  // Set text color to black for better visibility
        } else {
            holder.itemView.setBackgroundColor(android.graphics.Color.WHITE)  // Default background
            holder.suggestionText.setTextColor(android.graphics.Color.DKGRAY)  // Gray out text for other suggestions
        }

        // Handle the support button click to increase supporters count
        holder.supportButton.setOnClickListener {
            suggestion.supporters += 1
            suggestions.sortByDescending { it.supporters }  // Re-sort the list after updating the supporters count
            notifyItemChanged(position)  // Only notify for the changed item
        }
    }

    override fun getItemCount(): Int = suggestions.size
}

