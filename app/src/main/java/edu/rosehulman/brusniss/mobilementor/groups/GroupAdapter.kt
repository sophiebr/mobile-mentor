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

class GroupAdapter(private val context: Context, private val navController: NavController) : RecyclerView.Adapter<GroupViewHolder>() {

    private val groups = ArrayList<GroupModel>()
    private val groupRef = FirebaseFirestore.getInstance().collection(Constants.GROUP_PATH)

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
                                Log.d(Constants.TAG, "Inserted")
                                Log.d(Constants.TAG, "New size: ${groups.size}")
                            }
                            DocumentChange.Type.REMOVED -> {
                                val pos = groups.indexOfFirst { group.id == it.id }
                                groups.removeAt(pos)
                                notifyItemRemoved(pos)
                                Log.d(Constants.TAG, "Removed")
                            }
                            DocumentChange.Type.MODIFIED -> {
                                val pos = groups.indexOfFirst { group.id == it.id }
                                groups[pos] = group
                                notifyItemChanged(pos)
                                Log.d(Constants.TAG, "Modded")
                            }
                        }
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_group_forum_summary_row, parent, false)
        Log.d(Constants.TAG, "Making holder")
        return GroupViewHolder(view, this)
    }

    override fun getItemCount(): Int = groups.size

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        Log.d(Constants.TAG, "Going to bind")
        holder.bind(groups[position])
    }

    fun navigateToForum(pos: Int) {
        val args = Bundle().apply {
            putString("groupPath", groupRef.path + '/' + groups[pos].id)
        }
        navController.navigate(R.id.nav_forum, args)
    }
}