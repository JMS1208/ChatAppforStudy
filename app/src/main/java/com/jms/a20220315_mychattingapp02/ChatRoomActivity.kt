package com.jms.a20220315_mychattingapp02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jms.a20220315_mychattingapp02.Adapter.LeftMessageItemBinding
import com.jms.a20220315_mychattingapp02.Adapter.RightMessageItemBinding
import com.jms.a20220315_mychattingapp02.Model.ChatModel
import com.jms.a20220315_mychattingapp02.Model.User
import com.jms.a20220315_mychattingapp02.databinding.ActivityChatRoomBinding


import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

class ChatRoomActivity : AppCompatActivity() {
    val db = Firebase.firestore //얘는 클라우드 데이터베이스
    private val TAG = ChatRoomActivity::class.java.simpleName
    val database = FirebaseDatabase.getInstance() //얘는 실시간 데이터베이스
    val myRef = database.getReference("message")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        val myUid = intent.getStringExtra("myUid")
        // 내 Uid 가져오는 방법은 이것보다 이게 더 쉬움
        // var auth : FirebaseAuth 선언하고
        // auth=Firebase.auth
        // val myUid = auth.uid
        // 이렇게 찾는게 제일깔끔
        var myName :String? = null
        var leftUserName :String? = null
        val leftUserUid = intent.getStringExtra("leftUserUid")
        val docRef = db.collection("users").document("$leftUserUid")
        //상대방 이름 찾는용
        val adapter = GroupAdapter<GroupieViewHolder>()
        val docRef2 = db.collection("users").document("$myUid")
        //내 이름 찾는용
        //파이어베이스 DB보면 알수 있는데 먼저 collection으로 찾고 document로 찾고 get("필드명")으로 찾음

        /*
        docRef.get()
            .addOnSuccessListener {
                leftUserName=it.get("userName").toString()
                Log.d("확인용:","$leftUserUid , $leftUserName")
                adapter.add(LeftMessageItemBinding(User("$leftUserUid",leftUserName?:"상대방")))
                }

        docRef2.get()
            .addOnSuccessListener {
                myName=it.get("userName").toString()
                Log.d("확인용:","$myUid , $myName")
                adapter.add(RightMessageItemBinding(User("$myUid",myName?:"나")))

            }



         */

        //아래는 클라우드 데이터베이스로 저장된 데이터를 가져오는것
        //바로 바로 갱신이 안된다는 단점

        /*
        db.collection("message")
            .orderBy("time") // 시간 순서로 가져오기
            .get()
            .addOnSuccessListener {
                for (document in it) {
                    val senderUid = document.get("myUid")
                    val msg = document.get("message")
                    val receiverUid = document.get("yourUid")
                    if(senderUid==myUid){ //디비에서 찾은 센더 Uid랑 내 Uid랑 같으면 내가 보낸거니까
                        adapter.add(RightMessageItemBinding(User(myUid.toString(),myName.toString()?:"나"),msg.toString()))
                    } else {
                        adapter.add(LeftMessageItemBinding(User(receiverUid.toString(),"상대방"),msg.toString()))
                    }
                    binding.recyclerViewChatRoom.adapter = adapter
                }
            } // 바로바로 안뜨네

        */



        //아래는 실시간 데이터 베이스에서 데이터 가져오는거
        //바로바로 갱신되어야댐
        // Read from the database
        val readRef = database.getReference("message").child(myUid.toString()).child(leftUserUid.toString())
        readRef.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                //메시지 꺼내오는거
                val msg:String = snapshot.getValue(ChatModel::class.java)?.message.toString()
                val checkUid = snapshot.getValue(ChatModel::class.java)?.myUid //보낸사람의 Uid니까

                if(checkUid==myUid){//내가 보낸거
                    adapter.add(RightMessageItemBinding(User(myUid.toString(),myName.toString()),msg))
                }else{//상대방이 보낸거
                    adapter.add(LeftMessageItemBinding(User(checkUid.toString(),"상대방"),msg))
                // 나중에 이부분은 수정해야될듯 상대방의 이름을 알 수 있어야되는데 모르니까..
                }
                binding.recyclerViewChatRoom.adapter = adapter



            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })




        //아래는 클라우드 데이터 베이스에 저장하는거
        /*
        binding.button.setOnClickListener {
            val message:String = binding.messageEditText.text.toString()
            binding.messageEditText.text.clear()
            val chat = ChatModel(myUid!!,leftUserUid!!,message)
            //아래부터는 파이어베이스 DB에 새로 추가하는거임 message 콜렉션을 추가
            db.collection("message")
                .add(chat)
                .addOnSuccessListener {
                    Log.d(TAG,"DB에 메시지 넣기 성공")
                }
                .addOnFailureListener{
                    Log.e(TAG,"DB에 메시지 넣기 실패")
                }


        }

         */


        //얘는 실시간 데이터베이스에 보내는거거

       binding.button.setOnClickListener {
           val message:String = binding.messageEditText.text.toString()
           binding.messageEditText.text.clear()
           val chat = ChatModel(myUid!!,leftUserUid!!,message) //보내는 사람입장
           val chat_left=ChatModel(leftUserUid!!,myUid!!,message) //메시지 받는 사람 입장

           myRef.child(myUid.toString()).child(leftUserUid.toString()).push().setValue(chat)
           myRef.child(leftUserUid.toString()).child(myUid.toString()).push().setValue(chat_left)
        }

    }
}