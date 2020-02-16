package edu.rosehulman.brusniss.mobilementor.forum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.DocumentSnapshot
import edu.rosehulman.brusniss.mobilementor.Constants
import edu.rosehulman.brusniss.mobilementor.R
import kotlinx.android.synthetic.main.dialog_add_forum_post.view.*
import kotlinx.android.synthetic.main.fragment_gradient_background.*
import kotlinx.android.synthetic.main.fragment_gradient_background.view.*

class PublicForumFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_gradient_background, container, false)
        rootView.gradient_recycler_view.layoutManager = LinearLayoutManager(context)
        val adapter = ForumPostListAdapter(context!!, findNavController(), rootView.gradient_recycler_view.layoutManager as LinearLayoutManager)
        rootView.gradient_recycler_view.setHasFixedSize(true)
        rootView.gradient_recycler_view.adapter = adapter

        rootView.add_fab.setOnClickListener() {
            showAddPostDialog(adapter)
        }

        return rootView
    }

    private fun showAddPostDialog(adapter: ForumPostListAdapter) {
        val builder = AlertDialog.Builder(context!!)
        // Set options
        builder.setTitle("New Post")

        // Content is message, view, or list of items
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_forum_post, null, false)
        builder.setView(view)
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            val title = view.dialog_add_forum_post_title.text.toString()
            val content = view.dialog_add_forum_post_content.text.toString()
            if (!title.isNullOrBlank() && !title.isNullOrBlank()) {
                adapter.addNewPost(ForumPostModel(title = title, content = content))
            }
        }
        builder.setNegativeButton(android.R.string.cancel, null)

        builder.create().show()
    }
}