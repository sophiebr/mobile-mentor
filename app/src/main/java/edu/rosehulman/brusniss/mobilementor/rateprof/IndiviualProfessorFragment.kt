package edu.rosehulman.brusniss.mobilementor.rateprof

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import edu.rosehulman.brusniss.mobilementor.R
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

        rootView.rate_professor_write_reviews_title.setOnClickListener() {
            showAddReviewDialog(adapter)
        }
        rootView.professor_review_edit_image.setOnClickListener() {
            showAddReviewDialog(adapter)
        }

        return rootView
    }

    private fun loadHeader(rootView: View) {
        rootView.rate_professor_name.text = professor.name
        rootView.rate_professor_department.text = professor.department
        rootView.rate_professor_rating.rating = professor.quality
        rootView.rate_professor_rating_difficulty.rating = professor.difficulty
        //TODO: picture
    }

    private fun showAddReviewDialog(adapter: ReviewAdapter) {
        val builder = AlertDialog.Builder(context!!)
        // Set options
        builder.setTitle(getString(R.string.new_review_title))

        // Content is message, view, or list of items
        val view = LayoutInflater.from(context).inflate(R.layout.re, null, false)
        builder.setView(view)
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            val name = view.dialog_add_new_group_name.text.toString()
            val code = view.dialog_add_new_group_code.text.toString()
            if (!name.isBlank() && !code.isBlank()) {
                adapter.addExistingGroup(name, code.toInt(), context!!)
            }
        }
        builder.setNegativeButton(android.R.string.cancel, null)

        builder.create().show()
    }
}