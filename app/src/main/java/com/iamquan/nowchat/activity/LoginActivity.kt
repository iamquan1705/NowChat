package com.iamquan.nowchat.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.iamquan.nowchat.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mFireBase : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mFireBase = Firebase.auth
        binding.btnLogin.setOnClickListener {
            login(binding.edtEmail,binding.edtPassword)
        }

        binding.tvNoAccount.setOnClickListener {
            var intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)

        }
        binding.tvForgotPass.setOnClickListener {

        }
    }
    fun login(edtEmail:EditText,edtPassword:EditText) {
            var email = edtEmail.text.toString()
            var password = edtPassword.text.toString()
            if (email.isEmpty()) {
                edtEmail.error = "Email is requỉed"
            } else if (password.isEmpty()) {
                edtPassword.error = "Password is requỉed"
            } else if (password.length < 6 ) {
                edtPassword.error = "Password must be than 6 characters"
            } else {
                mFireBase.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        this.finish()
                    } else {
                        Toast.makeText(this,"Email or Password is wrong", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
    }
}