package edu.rosehulman.brusniss.mobilementor.rateprof

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import edu.rosehulman.brusniss.mobilementor.R
import edu.rosehulman.brusniss.mobilementor.User
import edu.rosehulman.brusniss.mobilementor.profile.PermissionLevel
import kotlinx.android.synthetic.main.dialog_add_rate_professor_review.view.*
import kotlinx.android.synthetic.main.fragment_rate_professor_individual_review.view.*

class IndiviualProfessorFragment : Fragment() {

    lateinit var professor: ProfModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var profRef = ""
        arguments?.let {
            professor = it.getParcelable("prof")!!
            profRef = it.getString("profRef")!!
        }

        val rootView = inflater.inflate(R.layout.fragment_rate_professor_individual_review, container, false)
        loadHeader(rootView)
        rootView.professor_review_recycler.layoutManager = LinearLayoutManager(context)
        val adapter = ReviewAdapter(context!!, profRef)
        rootView.professor_review_recycler.setHasFixedSize(true)
        rootView.professor_review_recycler.adapter = adapter

        if (User.permissionLevel != PermissionLevel.PROFESSOR) {
            rootView.rate_professor_write_reviews_title.setOnClickListener() {
                showAddReviewDialog(adapter, rootView)
            }
            rootView.professor_review_edit_image.setOnClickListener() {
                showAddReviewDialog(adapter, rootView)
            }
        } else {
            rootView.rate_professor_write_reviews_title.visibility = View.GONE
            rootView.professor_review_edit_image.visibility = View.GONE
        }

        return rootView
    }

    private fun loadHeader(rootView: View) {
        rootView.rate_professor_name.text = professor.name
        rootView.rate_professor_department.text = professor.department
        rootView.rate_professor_rating.rating = professor.quality
        rootView.rate_professor_rating_difficulty.rating = professor.difficulty
        if (professor.pictureUrl.isNotBlank()) {
            Picasso.get().load(professor.pictureUrl).resize(600,600).centerInside().into(rootView.professor_image)
        }
    }

    private fun showAddReviewDialog(adapter: ReviewAdapter, rootView: View) {
        val builder = AlertDialog.Builder(context!!)
        // Set options
        builder.setTitle(getString(R.string.new_review_title))

        // Content is message, view, or list of items
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_add_rate_professor_review, null, false)
        builder.setView(view)
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            val className = view.dialog_rate_professor_class.text.toString()
            val reviewContent = view.dialog_rate_professor_content.text.toString()
            var diff = view.dialog_rate_professor_difficulty.text.toString()
            var quality = view.dialog_rate_professor_quality.text.toString()
            if (className.isNotBlank() && reviewContent.isNotBlank() && diff.isNotBlank() && quality.isNotBlank()) {
                if (diff.toFloat() > 5) {
                    diff = "5"
                } else if (diff.toFloat() < 0) {
                    diff = "0"
                }

                if (quality.toFloat() > 5) {
                    quality = "5"
                } else if (quality.toFloat() < 0) {
                    quality = "0"
                }

                val review = ReviewModel(className, reviewContent, diff.toFloat(), quality.toFloat())
                adapter.addReview(review)
                val averages = adapter.calculateAverage(review)
                rootView.rate_professor_rating_difficulty.rating = averages.first
                rootView.rate_professor_rating.rating = averages.second
            }
        }
        builder.setNegativeButton(android.R.string.cancel, null)

        builder.create().show()
    }
}