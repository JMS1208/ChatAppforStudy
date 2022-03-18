package com.jms.a20220315_mychattingapp02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jms.a20220315_mychattingapp02.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.loginButtonLogin.setOnClickListener {
            val email=binding.emailEditText.text
            val password=binding.passwordEditText.text
            var info :String?=null
            if(email==null){
                info+="email "

            }
            if(password==null){
                info+="비밀번호 "
            }

            if(info!=null){
                Toast.makeText(applicationContext,"$info 을(를) 입력해주세요",Toast.LENGTH_SHORT).show()
            }else{
                auth.signInWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(applicationContext,"로그인 성공",Toast.LENGTH_SHORT).show()
                            val intent = Intent(this,ChatListActivity::class.java)
                            val uid = auth.uid
                            intent.putExtra("myUid",uid)
                            startActivity(intent)
                        } else {
                            Toast.makeText(applicationContext,"email, 비밀번호를 정확히 입력해주세요",Toast.LENGTH_SHORT).show()
                        }
                    }
            }


        }


    }
}