package com.iamquan.nowchat.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.iamquan.nowchat.databinding.ItemChatLeftBinding
import com.iamquan.nowchat.databinding.ItemChatUserBinding
import com.iamquan.nowchat.model.Chat
import de.hdodenhof.circleimageview.CircleImageView

class ChatAdapter (private var listChat : List<Chat>) : RecyclerView.Adapter<ChatAdapter.ViewHolder>(){
     private var MSG_TYPE_LEFT = 0
    val MSG_TYPE_RIGHT = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ChatAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItemViewType(position: Int): Int {
        return if (FirebaseAuth.getInstance().currentUser != null) {
            if (listChat[position].sender
                    .equals(FirebaseAuth.getInstance().currentUser!!.uid)) {
                    MSG_TYPE_RIGHT
            } else {
                    MSG_TYPE_LEFT
            }
        } else MSG_TYPE_LEFT
    }


    class ViewHolder (private var binding: ItemChatLeftBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(chat : Chat) {

        }
    }
}