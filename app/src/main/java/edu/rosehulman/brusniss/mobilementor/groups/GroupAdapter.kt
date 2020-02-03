package edu.rosehulman.brusniss.mobilementor.groups

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.brusniss.mobilementor.R
import edu.rosehulman.brusniss.mobilementor.forum.ForumPostListViewHolder

class GroupAdapter(private val context: Context, private val navController: NavController) : RecyclerView.Adapter<GroupViewHolder>() {

    private val groups = ArrayList<GroupModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_group_forum_summary_row, parent, false)
        return GroupViewHolder(view)
    }

    override fun getItemCount(): Int = groups.size

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(groups[position])
    }
}