package edu.rosehulman.brusniss.mobilementor.forum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import edu.rosehulman.brusniss.mobilementor.Constants
import edu.rosehulman.brusniss.mobilementor.R
import edu.rosehulman.brusniss.mobilementor.User
import edu.rosehulman.brusniss.mobilementor.groups.Group
import edu.rosehulman.brusniss.mobilementor.groups.GroupModel
import edu.rosehulman.brusniss.mobilementor.profile.PermissionLevel
import edu.rosehulman.brusniss.mobilementor.profile.ProfileModel
import kotlinx.android.synthetic.main.dialog_edit_response.view.*
import kotlinx.android.synthetic.main.fragment_forum_post.view.*

class ForumPostFragment : Fragment() {

    private var forumPostModel: ForumPostModel? = null
    private lateinit var respRef: CollectionReference
    private lateinit var postRef: DocumentReference
    private lateinit var groupRef: DocumentReference
    private lateinit var userGroupRef: DocumentReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            forumPostModel = it.getParcelable("post")
            respRef = FirebaseFirestore.getInstance().collection(it.getString("postPath")!! + "/response")
            postRef = FirebaseFirestore.getInstance().document(it.getString("postPath")!!)
            groupRef = FirebaseFirestore.getInstance().document(it.getString("groupPath")!!)
            userGroupRef = FirebaseFirestore.getInstance().document((it.getString("userGroup")!!))
        }

        val forumView = inflater.inflate(R.layout.fragment_forum_post, container, false)
        loadPostHeader(forumView.post_question_header)
        forumView.post_response_recycler_view.layoutManager = LinearLayoutManager(context)
        val adapter = ForumResponseAdapter(context!!, respRef)
        forumView.post_response_recycler_view.setHasFixedSize(true)
        forumView.post_response_recycler_view.adapter = adapter

        forumView.add_fab.setOnClickListener { _ ->
            showAddResponseDialog(adapter, forumView.post_question_header)
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
        postRef.get().addOnSuccessListener {
            ForumPostModel.fromSnapshot(it).author?.get()?.addOnSuccessListener {
                val profile = ProfileModel.fromSnapshot(it)
                header.post_author_name.text = profile.name
                if (profile.pictureUrl.isNotBlank()) {
                    Picasso.get().load(profile.pictureUrl).resize(200, 200).centerInside()
                        .into(header.author_picture)
                }
            }
        }
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

    private fun showAddResponseDialog(adapter: ForumResponseAdapter, header: LinearLayout) {
        val builder = AlertDialog.Builder(context!!)
        // Set options
        builder.setTitle(getString(R.string.new_response_dialog_title))

        // Content is message, view, or list of items
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_response, null, false)
        builder.setView(view)
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            val content = view.dialog_edit_response_content.text.toString()
            if (!content.isBlank()) {
                val authorRef = FirebaseFirestore.getInstance().document(User.firebasePath)
                Log.d(Constants.TAG, authorRef.path)
                adapter.addResponse(ForumResponseModel(content = content, author = authorRef))

                forumPostModel!!.responseCount += 1
                header.post_response_text.text = forumPostModel?.responseCount.toString()

                if (User.permissionLevel > PermissionLevel.REGULAR && forumPostModel?.author?.path != User.firebasePath) {
                    forumPostModel!!.mentorResponseCount += 1
                    if (forumPostModel!!.questionState != QuestionStatus.NOTIFICATION) {
                        forumPostModel!!.questionState = QuestionStatus.MENTOR_ANSWERED
                        header.post_response_status.setImageResource(R.drawable.ic_answered)
                    }
                    header.post_star_text.text = forumPostModel!!.mentorResponseCount.toString()
                }
                postRef.set(forumPostModel!!)
                groupRef.get().addOnSuccessListener {
                    val group = Group.fromSnapshot(it)
                    group.messages += 1
                    groupRef.set(group)

                    userGroupRef.get().addOnSuccessListener {
                        val model = GroupModel.fromSnapshot(it)
                        model.messagesSeen += 1
                        userGroupRef.set(model)
                    }
                }
            }
        }
        builder.setNegativeButton(android.R.string.cancel, null)

        builder.create().show()
    }
}