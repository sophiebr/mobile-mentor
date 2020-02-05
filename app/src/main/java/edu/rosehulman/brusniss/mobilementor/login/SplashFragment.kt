package edu.rosehulman.brusniss.mobilementor.login

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.rosehulman.brusniss.mobilementor.R
import kotlinx.android.synthetic.main.fragment_login.view.*

// Typical fragment that calls a MainActivity function
// when a button is pressed.
class SplashFragment : Fragment() {
    var listener: OnRosefireLoginButtonPressedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        view.button_login.setOnClickListener {
            listener?.onRosefireLoginButtonPressed()
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnRosefireLoginButtonPressedListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnLoginButtonPressedListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnRosefireLoginButtonPressedListener {
        fun onRosefireLoginButtonPressed()
    }
}
