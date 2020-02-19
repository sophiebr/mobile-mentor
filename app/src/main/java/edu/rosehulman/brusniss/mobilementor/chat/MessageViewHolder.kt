package edu.rosehulman.brusniss.mobilementor.chat

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.brusniss.mobilementor.User
import kotlinx.android.synthetic.main.fragment_chatroom_row.view.*

class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(model: MessageModel) {
        itemView.message_bubble.text = model.message
        if (model.sender?.path == User.firebasePath) {
            itemView.setBackgroundResource(android.R.color.holo_blue_light)
            itemView.message_bubble.setBackgroundResource(android.R.color.holo_blue_light)
        }
    }
}