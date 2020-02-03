package edu.rosehulman.brusniss.mobilementor.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.rosehulman.brusniss.mobilementor.R

class ForumPostFragment : Fragment() {

    var forumPostModel: ForumPostModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            forumPostModel = it.getParcelable("post")
        }
        return inflater.inflate(R.layout.fragment_forum_post, container, false)
    }
}