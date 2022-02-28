package com.iamquan.nowchat.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iamquan.nowchat.activity.ChatActivity
import com.iamquan.nowchat.activity.InfoActivity
import com.iamquan.nowchat.databinding.ItemFriendUserBinding
import com.iamquan.nowchat.databinding.ItemUserFragmentUserBinding
import com.iamquan.nowchat.model.User
import com.iamquan.nowchat.utils.Utils

class UserAdapter (private var context: Context,
                   private var listUser: List<User>
) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        return UserAdapter.ViewHolder(
            ItemUserFragmentUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), context
        )
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
       holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    class ViewHolder(private var binding: ItemUserFragmentUserBinding, private var context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvUserNameItemChat.text = user.username
            if (!user.avatar.equals(Utils.DEFAULT)) {
                Glide.with(context).load(user.avatar).into(binding.imgAvtItemChat)
            }
            binding.imgbtChat.setOnClickListener {
                val intent = Intent(context,ChatActivity::class.java)
                val id = user.id
                intent.putExtra(Utils.ID,id)
                context.startActivity(intent)
            }
            binding.imgbtInfo.setOnClickListener {
                val intent = Intent(context,InfoActivity::class.java)
                val id = user.id
                intent.putExtra(Utils.ID,id)
                context.startActivity(intent)
            }
        }
    }
}