package edu.rosehulman.brusniss.mobilementor.groups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.rosehulman.brusniss.mobilementor.R
import edu.rosehulman.brusniss.mobilementor.forum.ForumPostModel
import kotlinx.android.synthetic.main.dialog_add_forum_post.view.*
import kotlinx.android.synthetic.main.dialog_add_new_group.view.*
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
        val builder = AlertDialog.Builder(context!!)
        // Set options
        builder.setTitle("New Group")

        // Content is message, view, or list of items
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_new_group, null, false)
        builder.setView(view)
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            val name = view.dialog_add_new_group_name.text.toString()
            val code = view.dialog_add_new_group_code.text.toString()
            if (!name.isNullOrBlank() && !code.isNullOrBlank()) {
                adapter.addNewGroup(GroupModel(name, code.toInt()))
            }
        }
        builder.setNegativeButton(android.R.string.cancel, null)

        builder.create().show()
    }
}