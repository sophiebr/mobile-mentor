package edu.rosehulman.brusniss.mobilementor.chat

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class MessageModel(val message: String = "", val sender: @RawValue DocumentReference? = null) : Parcelable {
    @get:Exclude
    var id = ""
    @ServerTimestamp
    var sent: Timestamp? = null

    companion object {
        const val TIMESTAMP_KEY = "sent"

        fun fromSnapshot(snapshot: DocumentSnapshot): MessageModel {
            val msg = snapshot.toObject(MessageModel::class.java)!!
            msg.id = snapshot.id
            return msg
        }
    }
}