package edu.rosehulman.brusniss.mobilementor.groups

import android.os.Parcelable
import edu.rosehulman.brusniss.mobilementor.forum.ForumPostModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GroupModel(var groupName: String = "", var code: Int = 0, var forumPosts: List<ForumPostModel> = ArrayList<ForumPostModel>()) : Parcelable