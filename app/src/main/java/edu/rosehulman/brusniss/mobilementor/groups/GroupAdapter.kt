package edu.rosehulman.brusniss.mobilementor.groups

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import edu.rosehulman.brusniss.mobilementor.Constants
import edu.rosehulman.brusniss.mobilementor.R
import edu.rosehulman.brusniss.mobilementor.User

class GroupAdapter(private val context: Context, private val navController: NavController) : RecyclerView.Adapter<GroupViewHolder>() {

    private val groups = ArrayList<GroupModel>()
    private val groupRef = FirebaseFirestore.getInstance().collection(User.firebasePath + "/groups")

    init {
        groupRef.orderBy(GroupModel.NAME_KEY, Query.Direction.ASCENDING)
            .addSnapshotListener() { snapshot, exception ->
                if (exception != null) {
                    Log.e(Constants.TAG, "Listen error $exception")
                } else {
                    Log.d(Constants.TAG, "In Group listener")
                    for (docChange in snapshot!!.documentChanges) {
                        val group = GroupModel.fromSnapshot(docChange.document)
                        Log.d(Constants.TAG, group.name)
                        when (docChange.type) {
                            DocumentChange.Type.ADDED -> {
                                groups.add(0, group)
                                notifyItemInserted(0)
                            }
                            DocumentChange.Type.REMOVED -> {
                                val pos = groups.indexOfFirst { group.id == it.id }
                                groups.removeAt(pos)
                                notifyItemRemoved(pos)
                            }
                            DocumentChange.Type.MODIFIED -> {
                                val pos = groups.indexOfFirst { group.id == it.id }
                                groups[pos] = group
                                notifyItemChanged(pos)
                            }
                        }
                        notifyDataSetChanged()
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_group_forum_summary_row, parent, false)
        return GroupViewHolder(view, this)
    }

    override fun getItemCount(): Int = groups.size

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(groups[position])
    }

    fun navigateToForum(pos: Int) {
        val args = Bundle().apply {
            putString("groupPath", groups[pos].group?.path)
            putString("forumName", groups[pos].name)
        }
        navController.navigate(R.id.nav_forum, args)
    }

    fun addNewGroup(groupModel: GroupModel) {
        groupRef.add(groupModel)
    }
}