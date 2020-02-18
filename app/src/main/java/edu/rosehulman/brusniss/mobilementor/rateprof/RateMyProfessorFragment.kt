package edu.rosehulman.brusniss.mobilementor.rateprof

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.rosehulman.brusniss.mobilementor.R
import kotlinx.android.synthetic.main.fragment_rate_professor_summary.view.*

class RateMyProfessorFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_rate_professor_summary, container, false)
        rootView.professor_rating_summary_recycler_view.layoutManager = LinearLayoutManager(context)
        val adapter = ProfRowViewAdapter(context!!, findNavController())
        rootView.professor_rating_summary_recycler_view.setHasFixedSize(true)
        rootView.professor_rating_summary_recycler_view.adapter = adapter
        return rootView
    }
}