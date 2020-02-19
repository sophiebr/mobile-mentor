package edu.rosehulman.brusniss.mobilementor.chat

import android.os.Parcelable
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ChatroomModel(val receiver: String = "", val chat: @RawValue DocumentReference? = null) : Parcelable {
    @get:Exclude
    var id = ""

    // For the spinner to work using this model
    override fun toString(): String {
        return receiver
    }

    companion object {
        const val MENTOR_KEY = "receiver"

        fun fromSnapshot(snapshot: DocumentSnapshot): ChatroomModel {
            val chat = snapshot.toObject(ChatroomModel::class.java)!!
            chat.id = snapshot.id
            return chat
        }
    }
}