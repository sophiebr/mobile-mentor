package edu.rosehulman.brusniss.mobilementor.forum

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.rosehulman.brusniss.mobilementor.Constants
import edu.rosehulman.brusniss.mobilementor.R
import kotlinx.android.synthetic.main.fragment_gradient_background.view.*

private const val ARG_UID = "UID"

class PublicForumFragment : Fragment() {

    private var uid: String? = null

    companion object {
        @JvmStatic
        fun newInstance(uid: String) =
            PublicForumFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_UID, uid)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uid = it.getString(ARG_UID)
        }
    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val rootView = inflater.inflate(R.layout.fragment_gradient_background, container, false)
//        rootView.gradient_recycler_view.layoutManager = LinearLayoutManager(context)
//        val adapter = ForumPostListAdapter(context!!, findNavController(), rootView.gradient_recycler_view.layoutManager as LinearLayoutManager)
//        rootView.gradient_recycler_view.setHasFixedSize(true)
//        rootView.gradient_recycler_view.adapter = adapter
//        return rootView
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        try {
            val navControl = findNavController()
        } catch (e: Exception) {
            Log.d(Constants.TAG, e.toString())
        }

        return inflater.inflate(R.layout.fragment_gradient_background, container, false)
    }
}