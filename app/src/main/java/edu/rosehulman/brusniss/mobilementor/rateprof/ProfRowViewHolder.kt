package edu.rosehulman.brusniss.mobilementor.rateprof

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
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
        itemView.rating.rating = model.quality
        if (model.pictureUrl.isNotBlank()) {
            Picasso.get().load(model.pictureUrl).resize(200,200).centerCrop().into(itemView.professor_image)
        }
    }
}