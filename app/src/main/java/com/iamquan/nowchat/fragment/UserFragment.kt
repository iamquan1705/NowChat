package com.iamquan.nowchat.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.iamquan.nowchat.adapter.UserAdapter
import com.iamquan.nowchat.databinding.FragmentUserBinding
import com.iamquan.nowchat.vm.ListUserViewModel


class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
    private  val viewmodel : ListUserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(layoutInflater)

        viewmodel.allUser()
        viewmodel.listuser.observe(viewLifecycleOwner,{
            val adapter = UserAdapter(requireContext(),it)
            binding.rvUser.adapter = adapter
            binding.rvUser.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        })
        return binding.root
    }
}