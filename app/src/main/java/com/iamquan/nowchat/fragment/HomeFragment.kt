package com.iamquan.nowchat.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.iamquan.nowchat.adapter.UserChatAdapter
import com.iamquan.nowchat.adapter.UserChatedAdapter
import com.iamquan.nowchat.databinding.FragmentHomeBinding
import com.iamquan.nowchat.model.DataMess
import com.iamquan.nowchat.utils.Utils
import com.iamquan.nowchat.vm.ChatedViewModel


class HomeFragment() : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewmodelChated: ChatedViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        getUsersData()
        getUserChatedData()
        return binding.root
    }

    private fun getUsersData() {
        viewmodelChated.listuserchated.observe(viewLifecycleOwner, {
            val adapter = UserChatAdapter(requireContext(), it, 1)
            binding.rvList.adapter = adapter
            binding.rvList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        })
    }

    private fun getUserChatedData() {
        viewmodelChated.listchated.observe(viewLifecycleOwner, {
            var listid = arrayListOf<String>()
            var listdtm = arrayListOf<DataMess>()
            listdtm.add(it[it.size - 1])
            listid.add(it[it.size - 1].id)
            for (i in it.size - 1 downTo 0) {
                if (!listid.contains(it[i].id)) {
                    listdtm.add(it[i])
                    listid.add(it[i].id)
                }
            }
            val adapter = UserChatedAdapter(listdtm, requireContext())
            binding.rvChat.adapter = adapter
            binding.rvChat.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        })
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.edtSearch.isFocusable = false
    }
}