package edu.rosehulman.brusniss.mobilementor.rateprof

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
        return rootView
    }

    private fun loadHeader(rootView: View) {
        rootView.rate_professor_name.text = professor.name
        rootView.rate_professor_department.text = professor.department
        rootView.rate_professor_rating.rating = professor.rating
        //TODO: picture
    }
}