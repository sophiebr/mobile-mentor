package edu.rosehulman.brusniss.mobilementor.forum

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import edu.rosehulman.brusniss.mobilementor.Constants
import edu.rosehulman.brusniss.mobilementor.R
import edu.rosehulman.brusniss.mobilementor.groups.Group
import java.util.*

class ForumPostListAdapter(private val context: Context, private val navController: NavController, groupPath: String, private var layoutManager: LinearLayoutManager) : RecyclerView.Adapter<ForumPostListViewHolder>() {

    private val posts = ArrayList<ForumPostModel>()
    private val publicForumRef = FirebaseFirestore.getInstance().collection("$groupPath/forum");
    private val groupRef = FirebaseFirestore.getInstance().document(groupPath)

    init {
        publicForumRef.orderBy(ForumPostModel.TIMESTAMP_KEY, Query.Direction.ASCENDING)
            .addSnapshotListener() { snapshot, exception ->
                if (exception != null) {
                    Log.e(Constants.TAG, "Listen error $exception")
                } else {
                    Log.d(Constants.TAG, "In Forum listener")
                    for (docChange in snapshot!!.documentChanges) {
                        val post = ForumPostModel.fromSnapshot(docChange.document)
                        when (docChange.type) {
                            DocumentChange.Type.ADDED -> {
                                posts.add(0, post)
                                notifyItemInserted(0)
                                layoutManager.scrollToPosition(0)
                            }
                            DocumentChange.Type.REMOVED -> {
                                val pos = posts.indexOfFirst { post.id == it.id }
                                posts.removeAt(pos)
                                notifyItemRemoved(pos)
                            }
                            DocumentChange.Type.MODIFIED -> {
                                val pos = posts.indexOfFirst { post.id == it.id }
                                posts[pos] = post
                                notifyItemChanged(pos)
                            }
                        }
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumPostListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_forum_row, parent, false)
        return ForumPostListViewHolder(view, this)
    }

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: ForumPostListViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    fun navigateToPost(position: Int) {
        val args = Bundle().apply {
            putParcelable("post", posts[position])
            putString("postPath", publicForumRef.path + '/' + posts[position].id)
        }
        navController.navigate(R.id.nav_forum_post, args)
    }

    fun addNewPost(forumPostModel: ForumPostModel) {
        publicForumRef.add(forumPostModel)
        groupRef.get().addOnSuccessListener {
            val group = Group.fromSnapshot(it)
            group.messages += 1
            groupRef.set(group)
        }
    }
}