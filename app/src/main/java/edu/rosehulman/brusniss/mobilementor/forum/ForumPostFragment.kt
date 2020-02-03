package edu.rosehulman.brusniss.mobilementor.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.rosehulman.brusniss.mobilementor.R
import kotlinx.android.synthetic.main.fragment_forum_post.view.*
import kotlinx.android.synthetic.main.fragment_gradient_background.view.*

class ForumPostFragment : Fragment() {

    private var forumPostModel: ForumPostModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            forumPostModel = it.getParcelable("post")
        }
        val forumView = inflater.inflate(R.layout.fragment_forum_post, container, false)
        forumView.post_question_header.post_title_text.text = forumPostModel?.title
        // More initialization from the forum post model...
        forumView.post_response_recycler_view.layoutManager = LinearLayoutManager(context)
        val adapter = ForumResponseAdapter(context!!, forumPostModel!!.responses)
        forumView.post_response_recycler_view.setHasFixedSize(true)
        forumView.post_response_recycler_view.adapter = adapter
        return forumView
    }
}