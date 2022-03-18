package com.jms.a20220315_mychattingapp02

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jms.a20220315_mychattingapp02.Model.User
import com.jms.a20220315_mychattingapp02.Adapter.UserItemBinding
import com.jms.a20220315_mychattingapp02.databinding.ActivityChatListBinding

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class ChatListActivity : AppCompatActivity() {
    val db = Firebase.firestore

    private val TAG = "채팅"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChatListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        val myUid = intent.getStringExtra("myUid")
        val adapter = GroupAdapter<GroupieViewHolder>()

        db.collection("users") //데이터 읽어오는 과정정
           .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    adapter.add(UserItemBinding(User(document.id, document.get("userName").toString())))
                    Log.d(TAG,"이름: ${document.get("userName")}") //get으로 가져옴
                    Log.d(TAG, "DocumentSnapshot added with ID:${document.id} }")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error adding document", exception)
            }

        binding.recyclerViewList.adapter =  adapter

        adapter.setOnItemClickListener{ item, view->
            val leftUserUid= (item as UserItemBinding).user.uid
            val intent = Intent(this,ChatRoomActivity::class.java)
            intent.putExtra("leftUserUid",leftUserUid)
            intent.putExtra("myUid",myUid)
            startActivity(intent)
        }

        binding.myChatRoomButton.setOnClickListener {
            val intent = Intent(this, MyChatRoomActivity::class.java)
            startActivity(intent)

        }
    }
}