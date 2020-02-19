package edu.rosehulman.brusniss.mobilementor.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import edu.rosehulman.brusniss.mobilementor.Constants
import edu.rosehulman.brusniss.mobilementor.R
import edu.rosehulman.brusniss.mobilementor.User
import kotlinx.android.synthetic.main.fragment_chatroom.view.*

class ChatFragment : Fragment() {

    private val chatsRef = FirebaseFirestore.getInstance().collection("${User.firebasePath}/chats")
    private lateinit var spinnerAdapter: ArrayAdapter<ChatroomModel>

    init {
        chatsRef.orderBy(ChatroomModel.MENTOR_KEY, Query.Direction.ASCENDING)
            .addSnapshotListener() { snapshot, exception ->
                if (exception != null) {
                    Log.e(Constants.TAG, "Listen error $exception")
                } else {
                    Log.d(Constants.TAG, "In Chat listener")
                    for (docChange in snapshot!!.documentChanges) {
                        val chat = ChatroomModel.fromSnapshot(docChange.document)
                        when (docChange.type) {
                            DocumentChange.Type.ADDED -> {
                                spinnerAdapter.insert(chat, 0)
                            }
                            DocumentChange.Type.REMOVED -> {
                                spinnerAdapter.remove(chat)
                            }
                            DocumentChange.Type.MODIFIED -> {
                                for (index in 0 until spinnerAdapter.count) {
                                    val item = spinnerAdapter.getItem(index)!!
                                    if (item.id == chat.id) {
                                        spinnerAdapter.remove(item)
                                        spinnerAdapter.insert(chat, index)
                                        spinnerAdapter.notifyDataSetChanged()
                                        break
                                    }
                                }
                            }
                        }
                    }
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_chatroom, container, false)
        spinnerAdapter = ArrayAdapter(context!!, R.layout.spinner_item_line)
        spinnerAdapter.setNotifyOnChange(true)
        rootView.choose_chat_spinner.adapter = spinnerAdapter
        return rootView
    }
}