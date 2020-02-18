package edu.rosehulman.brusniss.mobilementor.groups

import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Group(var code: Int = 0, var messages: Int = 0) : Parcelable {
    companion object {
        fun fromSnapshot(snapshot: DocumentSnapshot): Group {
            return snapshot.toObject(Group::class.java)!!
        }
    }
}