package edu.rosehulman.brusniss.mobilementor.profile

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileModel(var name: String = "Slim Shady",
                        var major: String = "CHEM/MA",
                        @PermissionLevel var permissionLevel: Int = PermissionLevel.REGULAR,
                        var bio: String = "The alternate personality to Marshall Mathers"
                            ) : ViewModel(), Parcelable {

    fun hasPermission(@PermissionLevel level: Int): Boolean {
        return permissionLevel >= level
    }
}