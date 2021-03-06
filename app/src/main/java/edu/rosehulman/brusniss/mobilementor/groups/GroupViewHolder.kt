package edu.rosehulman.brusniss.mobilementor.groups

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.brusniss.mobilementor.Constants
import kotlinx.android.synthetic.main.fragment_group_forum_summary_row.view.*

class GroupViewHolder(itemView: View, adapter: GroupAdapter) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener { _ ->
            adapter.navigateToForum(adapterPosition)
        }
    }

    fun bind(model: GroupModel) {
        itemView.group_title_text.text = model.name
        model.group?.get()?.addOnSuccessListener {
            val group = Group.fromSnapshot(it)
            itemView.group_num_notifications_count_text.text = (group.messages - model.messagesSeen).toString()
        }
    }
}