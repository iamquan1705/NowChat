package com.iamquan.nowchat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iamquan.nowchat.databinding.ItemFriendUserBinding
import com.iamquan.nowchat.model.User
import com.iamquan.nowchat.utils.Utils

class UserChatAdapter(
    private var context: Context,
    private var listUser: List<User>,
    private var viewType: Int
) :
    RecyclerView.Adapter<UserChatAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemFriendUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),context
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size
    class ViewHolder(private var binding: ItemFriendUserBinding, private var context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.tvUserNameItemFriend.text = user.username
            if (!user.avatar.equals(Utils.DEFAULT)) {
                Glide.with(context).load(user.avatar).into(binding.imgAvtItemFriend)
            }
            if (user.status == 1) {
                binding.imgstatusOffItemFriend.setVisibility(View.GONE)
                binding.imgstatusOnItemFriend.setVisibility(View.VISIBLE)
            } else if (user.status == 0) {
                binding.imgstatusOffItemFriend.setVisibility(View.VISIBLE)
                binding.imgstatusOnItemFriend.setVisibility(View.GONE)
            } else {
                binding.imgstatusOffItemFriend.setVisibility(View.GONE)
                binding.imgstatusOnItemFriend.setVisibility(View.GONE)
            }
        }
    }
}