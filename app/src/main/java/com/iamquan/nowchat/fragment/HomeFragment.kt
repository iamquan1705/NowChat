package com.iamquan.nowchat.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.iamquan.nowchat.adapter.UserChatAdapter
import com.iamquan.nowchat.databinding.FragmentHomeBinding
import com.iamquan.nowchat.model.User
import com.iamquan.nowchat.vm.ListUserViewModel

class HomeFragment() : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewmodel: ListUserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        getUsersData()
        return binding.root
    }

    private fun getUsersData() {
        viewmodel.allUser()
        viewmodel.listuser.observe(viewLifecycleOwner, {
            val adapter = UserChatAdapter(requireContext(), it,1)
            binding.rvList.adapter = adapter
            binding.rvList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        })
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.edtSearch.isFocusable = false
    }
}