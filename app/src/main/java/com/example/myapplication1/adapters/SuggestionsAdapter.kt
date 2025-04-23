package com.example.myapplication1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.models.Suggestion
import java.text.SimpleDateFormat
import java.util.*

class SuggestionsAdapter(private val suggestions: List<Suggestion>) :
    RecyclerView.Adapter<SuggestionsAdapter.SuggestionViewHolder>() {

    class SuggestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val suggestionText: TextView = view.findViewById(R.id.textSuggestion1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_suggestion, parent, false)
        return SuggestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        val suggestion = suggestions[position]
        val date = Date(suggestion.timestamp) // This should work if timestamp is in milliseconds
        val formattedTime = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(date)
        holder.suggestionText.text = """
    Suggestion: ${suggestion.message}
    Posted by: ${suggestion.postedBy.name} (${suggestion.postedBy.rollNumber})
    Date: $formattedTime
    Supporters: ${suggestion.supporters.size}
""".trimIndent()
    }

    override fun getItemCount(): Int = suggestions.size
}