package edu.rosehulman.brusniss.mobilementor.forum

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.Query
import edu.rosehulman.brusniss.mobilementor.Constants
import edu.rosehulman.brusniss.mobilementor.R

class ForumResponseAdapter(private val context: Context, respRef: CollectionReference) : RecyclerView.Adapter<ForumResponseViewHolder>() {

    private val responses = ArrayList<ForumResponseModel>()

    init {
        Log.d(Constants.TAG, respRef.path)
        respRef.orderBy(ForumResponseModel.LIKE_KEY, Query.Direction.ASCENDING)
            .addSnapshotListener() { snapshot, exception ->
                if (exception != null) {
                    Log.e(Constants.TAG, "Listen error $exception")
                } else {
                    Log.d(Constants.TAG, "In Response listener")
                    for (docChange in snapshot!!.documentChanges) {
                        val response = ForumResponseModel.fromSnapshot(docChange.document)
                        Log.d(Constants.TAG, response.content)
                        when (docChange.type) {
                            DocumentChange.Type.ADDED -> {
                                responses.add(0, response)
                                notifyItemInserted(0)
                            }
                            DocumentChange.Type.REMOVED -> {
                                val pos = responses.indexOfFirst { response.timestamp == it.timestamp }
                                responses.removeAt(pos)
                                notifyItemRemoved(pos)
                            }
                            DocumentChange.Type.MODIFIED -> {
                                val pos = responses.indexOfFirst { response.timestamp == it.timestamp }
                                responses[pos] = response
                                notifyItemChanged(pos)
                            }
                        }
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumResponseViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_forum_post_response_row, parent, false)
        return ForumResponseViewHolder(view)
    }

    override fun getItemCount(): Int = responses.size

    override fun onBindViewHolder(holder: ForumResponseViewHolder, position: Int) {
        holder.bind(responses[position])
    }
}