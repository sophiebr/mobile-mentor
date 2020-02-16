package edu.rosehulman.brusniss.mobilementor.forum

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_forum_post.view.*
import kotlinx.android.synthetic.main.fragment_forum_row.view.*
import kotlinx.android.synthetic.main.fragment_forum_row.view.post_title_text
import kotlinx.android.synthetic.main.fragment_forum_row.view.post_like_text
import kotlinx.android.synthetic.main.fragment_forum_row.view.post_star_text

class ForumPostListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(model: ForumPostModel) {
        itemView.post_title_text.text = model.title
//        itemView.post_question_text.text = model.content
        itemView.post_like_text.text = model.likeCount.toString()
//        itemView.post_star_text.text = model.mentorResponseCount.toString()
//        itemView.post_edit_text.text = model.responseCount.toString()
    }
}