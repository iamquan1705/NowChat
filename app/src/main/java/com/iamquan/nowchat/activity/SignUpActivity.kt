package com.iamquan.nowchat.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.iamquan.nowchat.R
import com.iamquan.nowchat.databinding.ActivitySignUpBinding
import com.iamquan.nowchat.utils.Utils
import java.util.HashMap
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mFireBase: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mFireBase = FirebaseAuth.getInstance()
        binding.btnSignUp.setOnClickListener {
            signUp()
        }

    }

    private fun signUp() {
        var username = binding.edtUserNameSU.text.toString().trim()
        var email = binding.edtEmailSU.text.toString().trim()
        var password = binding.edtPasswordSU.text.toString().trim()
        var repassword = binding.edtRePasswordSU.text.toString().trim()
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(
                this,
                email,
                Toast.LENGTH_SHORT
            ).show()
        } else {
            if (username.isEmpty()) {
                binding.edtUserNameSU.error = "User name is required"
            } else if (email.isEmpty()) {
                binding.edtEmailSU.error = "Email is required"
            } else if (password.length < 6) {
                binding.edtPasswordSU.error = "Password must be than 6 characters"
            } else if (repassword.isEmpty() || password != repassword) {
                binding.edtRePasswordSU.error = "Password not matching"
            } else {
                mFireBase.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                     val uid = mFireBase.uid
                        val hashMap: HashMap<String, Any> = HashMap()
                        hashMap[Utils.ID] = uid!!
                        hashMap[Utils.EMAIL] = email
                        hashMap[Utils.USER_NAME] = username
                        hashMap[Utils.AVATAR] = Utils.DEFAULT
                        hashMap[Utils.PASSWORD] = password
                        hashMap[Utils.STATUS] = 1
                        hashMap[Utils.SEARCH] = username.lowercase()
                        val ref = FirebaseDatabase.getInstance().getReference(Utils.USERS)
                        ref.child(uid).setValue(hashMap).addOnSuccessListener {
                                Toast.makeText(
                                    this,
                                    "successfull",
                                    Toast.LENGTH_SHORT)
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                    } else {
                        Toast.makeText(
                            this,
                            "User is exist or email is not available",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }


}