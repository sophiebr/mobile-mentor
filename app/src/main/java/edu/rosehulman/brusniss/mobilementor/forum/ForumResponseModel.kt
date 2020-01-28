package edu.rosehulman.brusniss.mobilementor.forum

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForumResponseModel(var content: String = "Why?",
                              var author: String = "Dr. Dre",
                              var likeCount: Int = 0) : Parcelable