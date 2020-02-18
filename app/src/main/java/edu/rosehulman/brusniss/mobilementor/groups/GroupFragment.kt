package edu.rosehulman.brusniss.mobilementor.groups

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.rosehulman.brusniss.mobilementor.R
import kotlinx.android.synthetic.main.dialog_add_group.view.*
import kotlinx.android.synthetic.main.fragment_gradient_background.view.*

class GroupFragment : Fragment() {

    lateinit var adapter: GroupAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_gradient_background, container, false)
        rootView.gradient_recycler_view.layoutManager = LinearLayoutManager(context)
        adapter = GroupAdapter(context!!, findNavController())
        rootView.gradient_recycler_view.setHasFixedSize(true)
        rootView.gradient_recycler_view.adapter = adapter

        rootView.add_fab.hide()
        setHasOptionsMenu(true)

        return rootView
    }

    private fun showAddGroupDialog() {
        val builder = AlertDialog.Builder(context!!)
        // Set options
        builder.setTitle(getString(R.string.new_group_title))

        // Content is message, view, or list of items
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_group, null, false)
        builder.setView(view)
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            val name = view.dialog_add_new_group_name.text.toString()
            val code = view.dialog_add_new_group_code.text.toString()
            if (!name.isNullOrBlank() && !code.isNullOrBlank()) {
                adapter.addNewGroup(name, code.toInt())
            }
        }
        builder.setNegativeButton(android.R.string.cancel, null)

        builder.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.group_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_create_group -> {
                showAddGroupDialog()
                true
            }
            R.id.action_join_group -> {
                showJoinGroupDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showJoinGroupDialog() {
        val builder = AlertDialog.Builder(context!!)
        // Set options
        builder.setTitle("Join Existing Group")

        // Content is message, view, or list of items
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_group, null, false)
        builder.setView(view)
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            val name = view.dialog_add_new_group_name.text.toString()
            val code = view.dialog_add_new_group_code.text.toString()
            if (!name.isBlank() && !code.isBlank()) {
                adapter.addExistingGroup(name, code.toInt(), context!!)
            }
        }
        builder.setNegativeButton(android.R.string.cancel, null)

        builder.create().show()
    }
}