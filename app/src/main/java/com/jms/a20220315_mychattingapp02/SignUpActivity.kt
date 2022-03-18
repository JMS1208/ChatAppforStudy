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
import com.jms.a20220315_mychattingapp02.Model.User
import com.jms.a20220315_mychattingapp02.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        binding.joinButton.setOnClickListener {
            val userName = binding.userNameEditText.text
            val email = binding.emailEditText.text
            val password = binding.passwordEditText.text

            var info:String? = null
            if(userName==null){
                info+="유저이름 "
            }
            if(email==null){
                info+="이메일 "
            }
            if(password==null){
                info+="비밀번호 "
            }

            if(info!=null){
                Toast.makeText(applicationContext,"$info (을)를 정확히 입력하세요",Toast.LENGTH_SHORT).show()
            }else{
                auth.createUserWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(applicationContext,"회원가입 성공",Toast.LENGTH_SHORT).show()

                            val db = Firebase.firestore.collection("users")
                            val user = User(auth.uid!!.toString(),userName.toString())
                            db.document(auth.uid!!)
                                .set(user)
                                .addOnSuccessListener {
                                    Log.d("파이어베이스 DB","추가 성공")
                                }
                                .addOnFailureListener{
                                    Log.d("파이어베이스 DB","추가 실패")
                                }
                            val intent = Intent(this,ChatListActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(applicationContext,"회원가입 실패",Toast.LENGTH_SHORT).show()

                        }
                    }
            }


        }

    }
}