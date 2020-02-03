package edu.rosehulman.brusniss.mobilementor.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.rosehulman.brusniss.mobilementor.R
import kotlinx.android.synthetic.main.fragment_gradient_background.view.*

class PublicForumFragment : Fragment() {
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_gradient_background, container, false)
        setHasOptionsMenu(true)
        rootView.gradient_recycler_view.layoutManager = LinearLayoutManager(context)
        val adapter = ForumPostListAdapter(context!!, findNavController(), rootView.gradient_recycler_view.layoutManager as LinearLayoutManager)
        rootView.gradient_recycler_view.setHasFixedSize(true)
        rootView.gradient_recycler_view.adapter = adapter
        return rootView
    }
}