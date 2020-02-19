package edu.rosehulman.brusniss.mobilementor.rateprof

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.brusniss.mobilementor.R
import kotlinx.android.synthetic.main.fragment_rate_professor_individual_review_row.view.*

class ReviewViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {

    fun bind(model: ReviewModel) {
        itemView.professor_rating_quality.text = context.getString(R.string.two_sig_fig, model.quality)
        itemView.professor_rating_difficulty.text = context.getString(R.string.two_sig_fig, model.difficulty)
        itemView.professor_rating_class.text = model.className
        itemView.professor_individual_review_text.text = model.content
    }
}