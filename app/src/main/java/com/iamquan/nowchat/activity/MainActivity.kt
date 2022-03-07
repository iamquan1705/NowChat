package com.iamquan.nowchat.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.iamquan.nowchat.R
import com.iamquan.nowchat.databinding.ActivityMainBinding
import com.iamquan.nowchat.fragment.HomeFragment
import com.iamquan.nowchat.fragment.ProfileFragment
import com.iamquan.nowchat.fragment.UserFragment
import com.iamquan.nowchat.notification.FirebaseService

class MainActivity : AppCompatActivity() {
    private lateinit var binding :ActivityMainBinding
    private val homeFragment  = HomeFragment()
    private val profileFragment  = ProfileFragment()
    private val userFragment  = UserFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            FirebaseService.token = it.token
        }

        val currentUser = FirebaseAuth.getInstance().currentUser
        var userid = currentUser?.uid
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/$userid")

        replaceFragment(homeFragment)
        binding.navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navHome -> replaceFragment(homeFragment)
                R.id.navUser -> replaceFragment(userFragment)
                R.id.navProfile -> replaceFragment(profileFragment)
                else -> true
            }
            true
        }

    }

    private fun replaceFragment(fragment : Fragment) {
        if (fragment!=null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.flFlagment,fragment)
            transaction.commit()
        }
    }
}