package com.jms.a20220315_mychattingapp02.Model

class ChatModel(val myUid:String="", val yourUid:String="", val message:String="", val time : Long=System.currentTimeMillis()) {

//나중에 메시지 보낸시간도 넣어보자
}