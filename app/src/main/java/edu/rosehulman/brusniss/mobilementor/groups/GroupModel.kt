package edu.rosehulman.brusniss.mobilementor.groups

import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GroupModel(var name: String = "", var code: Int = 0) : Parcelable {
    @get:Exclude
    var id = ""

    companion object {
        const val NAME_KEY = "name"

        fun fromSnapshot(snapshot: DocumentSnapshot): GroupModel {
            val group = snapshot.toObject(GroupModel::class.java)!!
            group.id = snapshot.id
            return group
        }
    }
}