package edu.rosehulman.brusniss.mobilementor.forum

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ForumResponseModel(var content: String = "Why?",
                              var author: @RawValue DocumentReference? = null,
                              var likes: Int = 0) : Parcelable {
    @ServerTimestamp
    var timestamp: Timestamp? = null
    @get:Exclude
    var id = ""

    companion object {
        const val LIKE_KEY = "likes"

        fun fromSnapshot(snapshot: DocumentSnapshot): ForumResponseModel {
            val response = snapshot.toObject(ForumResponseModel::class.java)!!
            response.id = snapshot.id
            return response
        }
    }
}