package edu.rosehulman.brusniss.mobilementor.rateprof

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import edu.rosehulman.brusniss.mobilementor.Constants
import edu.rosehulman.brusniss.mobilementor.R

class ProfRowViewAdapter(private val context: Context, private val navController: NavController) : RecyclerView.Adapter<ProfRowViewHolder>() {

    private val professors = ArrayList<ProfRowViewModel>()
    private val profRef = FirebaseFirestore.getInstance().collection(Constants.RATE_PROF_PATH);
    init {
        profRef.orderBy(ProfRowViewModel.NAME_KEY, Query.Direction.ASCENDING)
            .addSnapshotListener() { snapshot, exception ->
                if (exception != null) {
                    Log.e(Constants.TAG, "Listen error $exception")
                } else {
                    Log.d(Constants.TAG, "In Professor listener")
                    for (docChange in snapshot!!.documentChanges) {
                        val prof = ProfRowViewModel.fromSnapshot(docChange.document)
                        when (docChange.type) {
                            DocumentChange.Type.ADDED -> {
                                professors.add(0, prof)
                                notifyItemInserted(0)
                            }
                            DocumentChange.Type.REMOVED -> {
                                val pos = professors.indexOfFirst { prof.id == it.id }
                                professors.removeAt(pos)
                                notifyItemRemoved(pos)
                            }
                            DocumentChange.Type.MODIFIED -> {
                                val pos = professors.indexOfFirst { prof.id == it.id }
                                professors[pos] = prof
                                notifyItemChanged(pos)
                            }
                        }
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfRowViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_rate_professor_summary_row, parent, false)
        return ProfRowViewHolder(view, this)
    }

    override fun getItemCount(): Int = professors.size

    override fun onBindViewHolder(holder: ProfRowViewHolder, position: Int) {
        holder.bind(professors[position])
    }

    fun navigateToProfessor(position: Int) {
        val args = Bundle().apply {
            putParcelable("prof", professors[position])
            putString("profRef", "${profRef.path}/${professors[position].id}")
        }
        navController.navigate(R.id.nav_rate_individual_prof, args)
    }
}