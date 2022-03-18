package com.jms.a20220315_mychattingapp02.Adapter

import android.view.View
import com.jms.a20220315_mychattingapp02.Model.User
import com.jms.a20220315_mychattingapp02.R
import com.jms.a20220315_mychattingapp02.databinding.ChatRightMeBinding
import com.xwray.groupie.viewbinding.BindableItem

class RightMessageItemBinding(private val user: User, private val message:String="메시지")
    : BindableItem<ChatRightMeBinding>() {
    override fun bind(viewBinding: ChatRightMeBinding, position: Int) {
        viewBinding.messageChatRight.text=message
        viewBinding.userNameChatRight.text=user.userName
    }

    override fun getLayout(): Int {
       return R.layout.chat_right_me
    }

    override fun initializeViewBinding(view: View): ChatRightMeBinding {
        return ChatRightMeBinding.bind(view)
    }


}