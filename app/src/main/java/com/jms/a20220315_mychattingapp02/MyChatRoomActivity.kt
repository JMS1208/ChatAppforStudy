package com.jms.a20220315_mychattingapp02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.jms.a20220315_mychattingapp02.databinding.ActivityMyChatRoomBinding

class MyChatRoomActivity : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()


    val myRef = database.getReference("message")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =  ActivityMyChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myRef.setValue("Hello, World!")

    }
}