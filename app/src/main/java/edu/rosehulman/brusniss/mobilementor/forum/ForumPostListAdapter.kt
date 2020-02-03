package edu.rosehulman.brusniss.mobilementor.forum

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.brusniss.mobilementor.R
import java.util.ArrayList

class ForumPostListAdapter(var context: Context) : RecyclerView.Adapter<ForumPostListViewHolder>() {

    private val posts = ArrayList<ForumPostModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumPostListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_forum_row, parent, false)
        return ForumPostListViewHolder(view)
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: ForumPostListViewHolder, position: Int) {
        holder.bind(posts[position])
    }
}