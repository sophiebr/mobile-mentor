package edu.rosehulman.brusniss.mobilementor

import com.google.firebase.firestore.FirebaseFirestore
import edu.rosehulman.brusniss.mobilementor.profile.PermissionLevel
import edu.rosehulman.brusniss.mobilementor.profile.ProfileModel

object User {
    @PermissionLevel var permissionLevel: Int = PermissionLevel.REGULAR
    var firebasePath: String = ""

    fun set(firebasePath: String) {
        val userRef = FirebaseFirestore.getInstance().document(firebasePath)
        userRef.get().addOnSuccessListener {
            permissionLevel = if (it.exists()) {
                val user = ProfileModel.fromSnapshot(it)
                user.permissionLevel
            } else {
                userRef.set(ProfileModel())
                PermissionLevel.REGULAR
            }
            this.firebasePath = firebasePath
        }
    }
}