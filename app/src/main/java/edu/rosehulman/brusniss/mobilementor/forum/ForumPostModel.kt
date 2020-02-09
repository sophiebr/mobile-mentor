package edu.rosehulman.brusniss.mobilementor.forum

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForumPostModel(var title: String = "How to drop out of Rose?",
                          var author: String = "Slim Shady",
                          var tags: List<String> = ArrayList<String>(),
                          var content: String = "I want to leave, somebody help.",
                          @QuestionStatus var questionState: Int = QuestionStatus.UNANSWERED,
                          var responses: List<ForumResponseModel> = ArrayList<ForumResponseModel>(),
                          var likeCount: Int = 0,
                          var responseCount: Int = 0,
                          var mentorResponseCount: Int = 0) : ViewModel(), Parcelable
@ServerTimestamp
var timestamp: Timestamp? = null