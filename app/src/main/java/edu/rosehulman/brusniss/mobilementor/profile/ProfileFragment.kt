package edu.rosehulman.brusniss.mobilementor.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import edu.rosehulman.brusniss.mobilementor.Constants
import edu.rosehulman.brusniss.mobilementor.R
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    private val userRef = FirebaseFirestore.getInstance().document(Constants.USER_PATH + "/" + FirebaseAuth.getInstance().currentUser?.uid)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val profileView = inflater.inflate(R.layout.fragment_profile, container, false)
        Log.d(Constants.TAG, userRef.path)
        userRef.get().addOnSuccessListener {
            val user = ProfileModel.fromSnapshot(it)
            profileView.profile_name_text.text = user.name
            profileView.profile_major_text.text = user.major
            profileView.profile_bio_edit_text.text = user.bio
            profileView.profile_level.text = when (user.permissionLevel) {
                PermissionLevel.ADMIN -> getString(R.string.admin)
                PermissionLevel.MENTOR -> getString(R.string.mentor)
                PermissionLevel.PROFESSOR -> getString(R.string.prof)
                else -> getString(R.string.reg)
            }
        }
        return profileView
    }
}