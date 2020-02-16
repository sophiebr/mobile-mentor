package edu.rosehulman.brusniss.mobilementor.forum

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class ForumPostModel(var title: String = "How to drop out of Rose?",
                          var author: @RawValue DocumentReference? = null,
                          var tags: List<String> = ArrayList<String>(),
                          var content: String = "I want to leave, somebody help.",
                          @QuestionStatus var questionState: Int = QuestionStatus.UNANSWERED,
                          var likeCount: Int = 0,
                          var responseCount: Int = 0,
                          var mentorResponseCount: Int = 0) : ViewModel(), Parcelable {

    @get:Exclude var id = ""
    @ServerTimestamp
    var timestamp: Timestamp? = null

    companion object {
        const val TIMESTAMP_KEY = "timestamp"

        fun fromSnapshot(snapshot: DocumentSnapshot): ForumPostModel {
            val post = snapshot.toObject(ForumPostModel::class.java)!!
            post.id = snapshot.id
            return post
        }
    }
}