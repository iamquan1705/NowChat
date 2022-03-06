package com.iamquan.nowchat.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.iamquan.nowchat.databinding.ActivityResetpasswordBinding

class ResetpasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetpasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetpasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var mFirebaseAuth = FirebaseAuth.getInstance()
        binding.btnReset.setOnClickListener {
            var email = binding.edtEmailReset.text.toString()
            if (!TextUtils.isEmpty(email)) {
                mFirebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this,"Please check your email",Toast.LENGTH_LONG).show()
                            var intent = Intent(this,LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this,
                              "Email not used for any user , please check again!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            }
        }
    }
}