package edu.rosehulman.brusniss.mobilementor.rateprof

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_rate_professor_summary_row.view.*

class ProfRowViewHolder(itemView: View, adapter: ProfRowViewAdapter) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener {
            adapter.navigateToProfessor(adapterPosition)
        }
    }

    fun bind(model: ProfModel) {
        itemView.rate_professor_name.text = model.name
        itemView.rate_professor_department.text = model.department
        itemView.rating.rating = model.rating
    }
}