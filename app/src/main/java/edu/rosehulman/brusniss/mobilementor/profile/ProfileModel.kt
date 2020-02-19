package edu.rosehulman.brusniss.mobilementor.profile

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileModel(var name: String = "Unknown Name",
                        @PermissionLevel var permissionLevel: Int = PermissionLevel.REGULAR,
                        var pictureUrl: String = "") : ViewModel(), Parcelable {
    companion object {
        fun fromSnapshot(snapshot: DocumentSnapshot): ProfileModel {
            return snapshot.toObject(ProfileModel::class.java)!!
        }
    }
}