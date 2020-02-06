package edu.rosehulman.brusniss.mobilementor.forum

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.rosehulman.brusniss.mobilementor.MainActivity
import edu.rosehulman.brusniss.mobilementor.R

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gradient_background, container, false)
    }
}