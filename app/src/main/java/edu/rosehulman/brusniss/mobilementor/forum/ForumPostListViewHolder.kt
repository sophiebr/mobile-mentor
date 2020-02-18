package edu.rosehulman.brusniss.mobilementor.forum

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.brusniss.mobilementor.R
import kotlinx.android.synthetic.main.fragment_forum_row.view.*

class ForumPostListViewHolder(itemView: View, adapter: ForumPostListAdapter) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener { v ->
            adapter.navigateToPost(adapterPosition)
        }
    }

    fun bind(model: ForumPostModel) {
        itemView.post_title_text.text = model.title
        itemView.post_like_text.text = model.likeCount.toString()
        itemView.post_star_text.text = model.mentorResponseCount.toString()
        itemView.post_edit_text.text = model.responseCount.toString()

        itemView.post_response_status.setImageResource(when (model.questionState) {
            QuestionStatus.MENTOR_ANSWERED -> R.drawable.ic_answered
            QuestionStatus.NOTIFICATION -> R.drawable.ic_forum_notification
            else -> R.drawable.ic_hourglass
        })
    }
}