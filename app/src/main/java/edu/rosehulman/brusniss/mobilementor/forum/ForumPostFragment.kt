package edu.rosehulman.brusniss.mobilementor.forum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.*
import edu.rosehulman.brusniss.mobilementor.Constants
import edu.rosehulman.brusniss.mobilementor.R
import edu.rosehulman.brusniss.mobilementor.User
import kotlinx.android.synthetic.main.dialog_edit_response.view.*
import kotlinx.android.synthetic.main.fragment_forum_post.view.*
import java.util.ArrayList

class ForumPostFragment : Fragment() {

    private var forumPostModel: ForumPostModel? = null
    private lateinit var respRef: CollectionReference
    private lateinit var postRef: DocumentReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            forumPostModel = it.getParcelable("post")
            respRef = FirebaseFirestore.getInstance().collection(it.getString("postPath")!! + "/response")
            postRef = FirebaseFirestore.getInstance().document(it.getString("postPath")!!)
        }

        val forumView = inflater.inflate(R.layout.fragment_forum_post, container, false)
        loadPostHeader(forumView.post_question_header)
        forumView.post_response_recycler_view.layoutManager = LinearLayoutManager(context)
        val adapter = ForumResponseAdapter(context!!, respRef)
        forumView.post_response_recycler_view.setHasFixedSize(true)
        forumView.post_response_recycler_view.adapter = adapter

        forumView.add_fab.setOnClickListener { _ ->
            showAddResponseDialog(adapter)
        }

        return forumView
    }

    private fun loadPostHeader(header: View) {
        header.post_title_text.text = forumPostModel?.title
        header.post_response_status.setImageResource(when (forumPostModel?.questionState) {
            QuestionStatus.MENTOR_ANSWERED -> R.drawable.ic_answered
            QuestionStatus.NOTIFICATION -> R.drawable.ic_forum_notification
            else ->  R.drawable.ic_hourglass
        })
        // TODO: UserModel to grab name and picture out of DocumentReference
        // TODO: Add tags
        header.post_question_text.text = forumPostModel?.content
        header.post_like_text.text = forumPostModel?.likeCount.toString()
        header.post_response_text.text = forumPostModel?.responseCount.toString()
        header.post_star_text.text = forumPostModel?.mentorResponseCount.toString()

        header.forum_like_image.setOnClickListener {
            if (forumPostModel?.author?.path != User.firebasePath) {
                forumPostModel!!.likeCount += 1
                header.post_like_text.text = forumPostModel?.likeCount.toString()
                postRef.set(forumPostModel!!)
            }
        }
    }

    private fun showAddResponseDialog(adapter: ForumResponseAdapter) {
        val builder = AlertDialog.Builder(context!!)
        // Set options
        builder.setTitle(getString(R.string.new_response_dialog_title))

        // Content is message, view, or list of items
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_response, null, false)
        builder.setView(view)
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            val content = view.dialog_edit_response_content.text.toString()
            if (!content.isNullOrBlank()) {
                val authorRef = FirebaseFirestore.getInstance().document(User.firebasePath)
                Log.d(Constants.TAG, authorRef.path)
                adapter.addResponse(ForumResponseModel(content = content, author = authorRef))
            }
        }
        builder.setNegativeButton(android.R.string.cancel, null)

        builder.create().show()
    }
}