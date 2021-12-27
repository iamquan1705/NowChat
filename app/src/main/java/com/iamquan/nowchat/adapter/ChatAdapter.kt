package com.iamquan.nowchat.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iamquan.nowchat.databinding.ItemChatUserBinding
import com.iamquan.nowchat.model.Chat

class ChatAdapter (private var listChat : List<Chat>) : RecyclerView.Adapter<ChatAdapter.ViewHolder>(){
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
        return super.getItemViewType(position)
    }
    class ViewHolder (private var binding : ItemChatUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(chat : Chat) {

        }
    }

}