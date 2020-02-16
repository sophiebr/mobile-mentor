package edu.rosehulman.brusniss.mobilementor.forum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import edu.rosehulman.brusniss.mobilementor.Constants
import edu.rosehulman.brusniss.mobilementor.R
import kotlinx.android.synthetic.main.fragment_forum_post.view.*
import java.util.ArrayList

class ForumPostFragment : Fragment() {

    private var forumPostModel: ForumPostModel? = null
    private lateinit var respRef: CollectionReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            forumPostModel = it.getParcelable("post")
            respRef = FirebaseFirestore.getInstance().collection(it.getString("responsePath")!!)
        }

        val forumView = inflater.inflate(R.layout.fragment_forum_post, container, false)
        loadPostHeader(forumView.post_question_header)
        forumView.post_response_recycler_view.layoutManager = LinearLayoutManager(context)
        val adapter = ForumResponseAdapter(context!!, respRef)
        forumView.post_response_recycler_view.setHasFixedSize(true)
        forumView.post_response_recycler_view.adapter = adapter
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
    }
}