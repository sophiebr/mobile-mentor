package edu.rosehulman.brusniss.mobilementor.groups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.rosehulman.brusniss.mobilementor.R
import edu.rosehulman.brusniss.mobilementor.forum.ForumPostListAdapter
import kotlinx.android.synthetic.main.fragment_gradient_background.view.*

class GroupFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_gradient_background, container, false)
        rootView.gradient_recycler_view.layoutManager = LinearLayoutManager(context)
        val adapter = GroupAdapter(context!!, findNavController())
        rootView.gradient_recycler_view.setHasFixedSize(true)
        rootView.gradient_recycler_view.adapter = adapter

        rootView.add_fab.setOnClickListener() {
            showAddGroupDialog(adapter)
        }

        return rootView
    }

    private fun showAddGroupDialog(adapter: GroupAdapter) {
        // TODO
    }
}