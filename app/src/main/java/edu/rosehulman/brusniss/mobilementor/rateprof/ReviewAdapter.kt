package edu.rosehulman.brusniss.mobilementor.rateprof

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import edu.rosehulman.brusniss.mobilementor.Constants
import edu.rosehulman.brusniss.mobilementor.R

class ReviewAdapter(private val context: Context, profRefPath: String) : RecyclerView.Adapter<ReviewViewHolder>() {

    private val reviews = ArrayList<ReviewModel>()
    private val reviewRef = FirebaseFirestore.getInstance().collection("$profRefPath/reviews")
    private val profRef = FirebaseFirestore.getInstance().document(profRefPath)

    init {
        reviewRef.orderBy(ReviewModel.TIMESTAMP_KEY, Query.Direction.ASCENDING)
            .addSnapshotListener() { snapshot, exception ->
                if (exception != null) {
                    Log.e(Constants.TAG, "Listen error $exception")
                } else {
                    Log.d(Constants.TAG, "In Professor listener")
                    for (docChange in snapshot!!.documentChanges) {
                        val review = ReviewModel.fromSnapshot(docChange.document)
                        when (docChange.type) {
                            DocumentChange.Type.ADDED -> {
                                reviews.add(0, review)
                                notifyItemInserted(0)
                            }
                            DocumentChange.Type.REMOVED -> {
                                val pos = reviews.indexOfFirst { review.id == it.id }
                                reviews.removeAt(pos)
                                notifyItemRemoved(pos)
                            }
                            DocumentChange.Type.MODIFIED -> {
                                val pos = reviews.indexOfFirst { review.id == it.id }
                                reviews[pos] = review
                                notifyItemChanged(pos)
                            }
                        }
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_rate_professor_individual_review_row, parent, false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int = reviews.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    fun addReview(review: ReviewModel) {
        reviewRef.add(review)
        calculateAverage()
    }

    private fun calculateAverage() {
        var avgDif = 0.0f
        var avgQual = 0.0f
        for (review in reviews) {
            avgDif += review.difficulty
            avgQual += review.quality
        }
        avgDif /= reviews.size
        avgQual /= reviews.size
        profRef.update("difficulty", avgDif)
        profRef.update("quality", avgQual)
    }
}