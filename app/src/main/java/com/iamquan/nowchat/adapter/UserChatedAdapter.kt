package com.iamquan.nowchat.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iamquan.nowchat.R
import com.iamquan.nowchat.activity.ChatActivity
import com.iamquan.nowchat.databinding.ItemChatUserBinding
import com.iamquan.nowchat.model.DataMess
import com.iamquan.nowchat.utils.Utils

class UserChatedAdapter(private val listUserChated: List<DataMess>, private val context: Context) :
    RecyclerView.Adapter<UserChatedAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserChatedAdapter.ViewHolder {
        return ViewHolder(
            ItemChatUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), context
        )
    }

    override fun onBindViewHolder(holder: UserChatedAdapter.ViewHolder, position: Int) {
        holder.bind(listUserChated[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            val id = listUserChated[position].id
            intent.putExtra(Utils.ID,id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listUserChated.size

    class ViewHolder(private val binding: ItemChatUserBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataMess: DataMess) {
            if (dataMess.id.equals(dataMess.lastUser)) {
                binding.tvLastMessenger.text = dataMess.lastmessage
            } else {
                binding.tvLastMessenger.text = "You :" + dataMess.lastmessage
            }
            Glide.with(context).load(dataMess.avatar).error(R.drawable.icon_username).into(binding.imgAvtItemChat)
            binding.tvUserNameItemChat.text = dataMess.userNameReceiver


        }

    }

}