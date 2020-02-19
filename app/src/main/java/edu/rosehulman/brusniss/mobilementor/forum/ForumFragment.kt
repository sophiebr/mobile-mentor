package edu.rosehulman.brusniss.mobilementor.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import edu.rosehulman.brusniss.mobilementor.Constants
import edu.rosehulman.brusniss.mobilementor.R
import edu.rosehulman.brusniss.mobilementor.User
import kotlinx.android.synthetic.main.dialog_add_forum_post.view.*
import kotlinx.android.synthetic.main.fragment_gradient_background.view.*

class ForumFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var groupPath = Constants.PUBLIC_FORUM_PATH
        var userGroup: String = ""
        arguments?.let {
            groupPath = it.getString("groupPath")!!
            userGroup = it.getString("userGroup")!!
            activity?.findViewById<Toolbar>(R.id.toolbar)?.title = it.getString("forumName")!! + " Forum"
        }
        val rootView = inflater.inflate(R.layout.fragment_gradient_background, container, false)
        rootView.gradient_recycler_view.layoutManager = LinearLayoutManager(context)
        val adapter = ForumPostListAdapter(context!!, findNavController(), groupPath, userGroup, rootView.gradient_recycler_view.layoutManager as LinearLayoutManager)
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
        builder.setTitle(getString(R.string.new_post_dialog_title))

        // Content is message, view, or list of items
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_forum_post, null, false)
        builder.setView(view)
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            val title = view.dialog_add_forum_post_title.text.toString()
            val tags = view.dialog_add_forum_post_tags.text.toString()
            val content = view.dialog_add_forum_post_content.text.toString()
            if (!title.isBlank() && !content.isBlank()) {
                val authorRef = FirebaseFirestore.getInstance().document(User.firebasePath)
                val post = ForumPostModel(title = title, content = content, author = authorRef)
                if (tags.isNotBlank()) {
                    post.tags = tags.split(",")
                }
                if (view.checkbox_post_as_notice.isChecked) {
                    post.questionState = QuestionStatus.NOTIFICATION
                }
                adapter.addNewPost(post)
            }
        }
        builder.setNegativeButton(android.R.string.cancel, null)

        builder.create().show()
    }
}