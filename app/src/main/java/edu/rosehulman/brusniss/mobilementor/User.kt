package edu.rosehulman.brusniss.mobilementor

import com.google.firebase.firestore.FirebaseFirestore
import edu.rosehulman.brusniss.mobilementor.profile.PermissionLevel
import edu.rosehulman.brusniss.mobilementor.profile.ProfileModel
import kotlinx.android.synthetic.main.fragment_profile.view.*

object User {
    var name: String = ""
    @PermissionLevel var permissionLevel: Int = 0
    var pictureUrl: String = ""
    var firebasePath: String = ""

    fun set(firebasePath: String) {
        val userRef = FirebaseFirestore.getInstance().document(firebasePath)
        userRef.get().addOnSuccessListener {
            val user = ProfileModel.fromSnapshot(it)
            name = user.name
            permissionLevel = user.permissionLevel
            this.firebasePath = firebasePath
        }
    }
}