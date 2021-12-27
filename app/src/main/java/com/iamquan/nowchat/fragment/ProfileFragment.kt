package com.iamquan.nowchat.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.iamquan.nowchat.R
import com.iamquan.nowchat.adapter.UserChatAdapter
import com.iamquan.nowchat.databinding.FragmentProfileBinding
import com.iamquan.nowchat.model.User
import com.iamquan.nowchat.utils.Utils


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        getProfileUser()
        var bottomsheet  = BottomSheetFragment()
        binding.imgImageProfile.setOnClickListener {
            BottomSheetFragment().show(childFragmentManager,"ABC")
        }
        if (bottomsheet.isCheckSetAvatar()) {
            binding.imgAvtProfile.setImageBitmap(bottomsheet.bmAvatar)
        }
        return binding.root
    }
    fun getProfileUser() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val reference = FirebaseDatabase.getInstance().getReference(Utils.USERS).child(currentUser.uid)
            reference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)
                        binding.tvEmailProfile.text = user?.email
                        binding.tvNameProfile.text = user?.username
                        binding.tvPasswordProfile.text = user?.password
                        if (user?.status == 1) {
                            binding.swStatus.isChecked = true
                        } else if (user?.status == 0) {
                            binding.swStatus.isChecked = false
                        }
                        if (!user?.avatar.equals(Utils.DEFAULT)) {
                            Glide.with(requireContext()).load(user?.avatar).into(binding.imgAvtProfile)
                        }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}