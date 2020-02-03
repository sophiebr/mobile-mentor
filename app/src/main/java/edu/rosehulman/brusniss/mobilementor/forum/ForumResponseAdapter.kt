package edu.rosehulman.brusniss.mobilementor.forum

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.brusniss.mobilementor.R

class ForumResponseAdapter(private val context: Context, private val responses: List<ForumResponseModel>) : RecyclerView.Adapter<ForumResponseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumResponseViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_forum_post_response_row, parent, false)
        return ForumResponseViewHolder(view)
    }

    override fun getItemCount(): Int = responses.size

    override fun onBindViewHolder(holder: ForumResponseViewHolder, position: Int) {
        holder.bind(responses[position])
    }
}