package edu.rosehulman.brusniss.mobilementor.forum

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.brusniss.mobilementor.R
import java.util.ArrayList

class ForumPostListAdapter(private val context: Context, private val navController: NavController, private var layoutManager: LinearLayoutManager) : RecyclerView.Adapter<ForumPostListViewHolder>() {

    private val posts = ArrayList<ForumPostModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumPostListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_forum_row, parent, false)
        return ForumPostListViewHolder(view)
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: ForumPostListViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    fun navigateToPost(position: Int) {
        val args = Bundle().apply {
            putParcelable("post", posts[position])
        }
        navController.navigate(R.id.nav_forum_post, args)
    }
}