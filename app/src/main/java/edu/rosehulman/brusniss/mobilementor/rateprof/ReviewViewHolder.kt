package edu.rosehulman.brusniss.mobilementor.rateprof

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_rate_professor_individual_review_row.view.*

class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(model: ReviewModel) {
        itemView.professor_rating_quality.text = model.quality.toString()
        itemView.professor_rating_difficulty.text = model.difficulty.toString()
        itemView.professor_rating_class.text = model.className
        itemView.professor_individual_review_text.text = model.content
    }
}