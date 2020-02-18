package edu.rosehulman.brusniss.mobilementor.forum

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_forum_post_response_row.view.*

class ForumResponseViewHolder(itemView: View, private var adapter: ForumResponseAdapter) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.forum_like_image.setOnClickListener {
            itemView.post_like_text.text = adapter.incLikeCount(adapterPosition).toString()
        }
    }

    fun bind(model: ForumResponseModel) {
        itemView.post_response_text.text = model.content
        itemView.post_like_text.text = model.likes.toString()
    }
}