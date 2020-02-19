package edu.rosehulman.brusniss.mobilementor.forum

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.brusniss.mobilementor.R

class TagAdapter(private val context: Context, private val tags: List<String>) : RecyclerView.Adapter<TagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.tag_item, parent, false)
        return TagViewHolder(view)
    }

    override fun getItemCount(): Int = tags.size

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(tags[position])
    }
}