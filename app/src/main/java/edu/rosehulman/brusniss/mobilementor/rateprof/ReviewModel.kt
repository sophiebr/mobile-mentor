package edu.rosehulman.brusniss.mobilementor.rateprof

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewModel(val className: String = "", val content: String = "", val difficulty: Float = 2.5f, val quality: Float = 2.5f) : Parcelable {
    @get:Exclude
    var id = ""
    @ServerTimestamp
    var timestamp: Timestamp? = null

    companion object {
        const val TIMESTAMP_KEY = "timestamp"

        fun fromSnapshot(snapshot: DocumentSnapshot): ReviewModel {
            val rev = snapshot.toObject(ReviewModel::class.java)!!
            rev.id = snapshot.id
            return rev
        }
    }
}