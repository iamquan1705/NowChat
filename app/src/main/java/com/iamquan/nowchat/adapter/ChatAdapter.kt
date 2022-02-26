package com.iamquan.nowchat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.iamquan.nowchat.R
import com.iamquan.nowchat.databinding.ItemChatLeftBinding
import com.iamquan.nowchat.databinding.ItemChatRightBinding
import com.iamquan.nowchat.model.Chat

class ChatAdapter(
    private var listChat: List<Chat> ,private val context:Context,private val mAvatar:String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var MSG_TYPE_LEFT = 0
    var MSG_TYPE_RIGHT = 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == MSG_TYPE_LEFT) {
            return ChatAdapter.LeftViewHolder(
                ItemChatLeftBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),context,mAvatar
            )
        } else {
            return ChatAdapter.RightViewHolder(
                ItemChatRightBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),context,mAvatar
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position)==MSG_TYPE_LEFT) {
             holder as LeftViewHolder
             holder.bind(listChat[position])
        } else {
            holder as RightViewHolder
            holder.bind(listChat[position])
        }
    }

    override fun getItemCount(): Int = listChat.size

    override fun getItemViewType(position: Int): Int {
        return if (FirebaseAuth.getInstance().currentUser != null) {
            if (listChat[position].senderid.equals(FirebaseAuth.getInstance().currentUser!!.uid)
            ) {
                MSG_TYPE_RIGHT
            } else {
                MSG_TYPE_LEFT
            }
        } else MSG_TYPE_LEFT
    }

    class LeftViewHolder(private var binding: ItemChatLeftBinding,private val context: Context,private val mAvatar: String) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.message.text = chat.message
            Glide.with(context).load(mAvatar).error(R.mipmap.ic_launcher).into(binding.avtUser)
        }
    }

    class RightViewHolder(private var binding: ItemChatRightBinding,private val context: Context,private val mAvatar: String) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            binding.message.text = chat.message

        }
    }
}