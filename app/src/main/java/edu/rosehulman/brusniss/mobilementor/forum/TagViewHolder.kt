package edu.rosehulman.brusniss.mobilementor.forum

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.tag_item.view.*

class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(tag: String) {
        itemView.tag_button.text = tag.trim()
    }
}