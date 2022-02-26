package com.iamquan.nowchat.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.iamquan.nowchat.databinding.ActivityInfoBinding
import com.iamquan.nowchat.utils.Utils
import com.iamquan.nowchat.vm.UserViewModel

class InfoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityInfoBinding
    private val userViewHodel : UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var id = intent.getStringExtra(Utils.ID).toString()
        displayInfo(id)
        binding.tvBack.setOnClickListener {
            finish()
        }
    }

    private fun displayInfo(id:String) {
        userViewHodel.getUserById(id)
        userViewHodel.user.observe(this,{
            binding.tvNameInfo.text = it.username
            binding.tvBirthday.text = it.birthday
            binding.tvEmail.text = it.email
            binding.tvPhone.text = it.phone
            binding.tvSex.text = it.sex
            Glide.with(this).load(it.avatar).into(binding.imgAvtInfo)
        })
    }
}