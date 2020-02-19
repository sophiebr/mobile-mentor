package edu.rosehulman.brusniss.mobilementor.chat

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import edu.rosehulman.brusniss.mobilementor.Constants
import edu.rosehulman.brusniss.mobilementor.R
import edu.rosehulman.brusniss.mobilementor.User
import kotlinx.android.synthetic.main.spinner_item_line.view.*
import java.util.*

class MessageAdapter(private val context: Context, private val arr: ArrayAdapter<ChatroomModel>, private var layoutManager: LinearLayoutManager) : RecyclerView.Adapter<MessageViewHolder>(), AdapterView.OnItemSelectedListener {

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        listener?.remove()
        messages.clear()
        messagesRef = arr.getItem(position)?.chat!!.collection("messages")
        setUpListener()
    }

    private val messages = ArrayList<MessageModel>()
    private lateinit var messagesRef: CollectionReference
    private var listener: ListenerRegistration? = null

    private fun setUpListener() {
        listener = messagesRef.orderBy(MessageModel.TIMESTAMP_KEY, Query.Direction.ASCENDING)
            .addSnapshotListener() { snapshot, exception ->
                if (exception != null) {
                    Log.e(Constants.TAG, "Listen error $exception")
                } else {
                    Log.d(Constants.TAG, "In Forum listener")
                    for (docChange in snapshot!!.documentChanges) {
                        val msg = MessageModel.fromSnapshot(docChange.document)
                        when (docChange.type) {
                            DocumentChange.Type.ADDED -> {
                                messages.add(msg)
                                notifyItemInserted(messages.size - 1)
                                layoutManager.scrollToPosition(messages.size - 1)
                            }
                            DocumentChange.Type.REMOVED -> {
                                val pos = messages.indexOfFirst { msg.id == it.id }
                                messages.removeAt(pos)
                                notifyItemRemoved(pos)
                            }
                            DocumentChange.Type.MODIFIED -> {
                                val pos = messages.indexOfFirst { msg.id == it.id }
                                messages[pos] = msg
                                notifyItemChanged(pos)
                            }
                        }
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_chatroom_row, parent, false)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    fun addMessage(msg: String) {
        val message = MessageModel(msg, FirebaseFirestore.getInstance().document(User.firebasePath))
        messagesRef.add(message)
    }
}