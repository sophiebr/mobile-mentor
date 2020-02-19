package edu.rosehulman.brusniss.mobilementor.profile

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileModel(var name: String = "Unknown Name",
                        var major: String = "Unknown Major",
                        @PermissionLevel var permissionLevel: Int = PermissionLevel.REGULAR,
                        var bio: String = "N/A",
                        var pictureUrl: String = "") : ViewModel(), Parcelable {
    companion object {
        fun fromSnapshot(snapshot: DocumentSnapshot): ProfileModel {
            return snapshot.toObject(ProfileModel::class.java)!!
        }
    }
}