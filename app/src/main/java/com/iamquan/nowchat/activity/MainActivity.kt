package com.iamquan.nowchat.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.iamquan.nowchat.R
import com.iamquan.nowchat.databinding.ActivityMainBinding
import com.iamquan.nowchat.fragment.HomeFragment
import com.iamquan.nowchat.fragment.ProfileFragment
import com.iamquan.nowchat.fragment.UserFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding :ActivityMainBinding


    private val homeFragment  = HomeFragment()
    private val profileFragment  = ProfileFragment()
    private val userFragment  = UserFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
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