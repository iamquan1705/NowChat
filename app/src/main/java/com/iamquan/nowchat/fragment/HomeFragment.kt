package com.iamquan.nowchat.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.iamquan.nowchat.R
import com.iamquan.nowchat.adapter.UserChatAdapter
import com.iamquan.nowchat.databinding.FragmentHomeBinding
import com.iamquan.nowchat.model.User
import com.iamquan.nowchat.utils.Utils

class HomeFragment() : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var listUser = arrayListOf<User>()
    private lateinit var mFireAuth: FirebaseAuth
    private lateinit var mUser: FirebaseUser
    private lateinit var adapter: UserChatAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        getUsersData()
        return binding.root
    }

    private fun getUsersData() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val reference = FirebaseDatabase.getInstance().getReference(Utils.USERS)
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    listUser.clear()
                    for (dataSnapshot in snapshot.children) {
                        val user = dataSnapshot.getValue(User::class.java)
                        if (user != null) {
                            if (!user.id.equals(currentUser.uid)) {
                                listUser.add(user)
                            }
                        }
                    }
                    adapter = UserChatAdapter(requireContext(), listUser, 1)
                    binding.rvList.adapter = adapter
                    binding.rvList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.edtSearch.isFocusable =false
    }
}