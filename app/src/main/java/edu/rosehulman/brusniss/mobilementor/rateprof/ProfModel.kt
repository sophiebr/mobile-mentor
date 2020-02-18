package edu.rosehulman.brusniss.mobilementor.rateprof

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfModel(val name: String = "", val department: String = "", val rating: Float = 0f, val pictureUrl: String = "") : Parcelable {
    @get:Exclude
    var id: String = ""

    companion object {
        const val NAME_KEY = "name"

        fun fromSnapshot(snapshot: QueryDocumentSnapshot): ProfModel {
            val prof = snapshot.toObject(ProfModel::class.java)!!
            prof.id = snapshot.id
            return prof
        }
    }
}