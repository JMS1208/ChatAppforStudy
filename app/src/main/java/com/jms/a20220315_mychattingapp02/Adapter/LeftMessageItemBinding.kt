package com.jms.a20220315_mychattingapp02.Adapter

import android.view.View
import com.jms.a20220315_mychattingapp02.Model.User
import com.jms.a20220315_mychattingapp02.R
import com.jms.a20220315_mychattingapp02.databinding.ChatLeftYouBinding
import com.xwray.groupie.viewbinding.BindableItem

class LeftMessageItemBinding(private val user: User, private val message: String="메시지")
    : BindableItem<ChatLeftYouBinding>() {
    override fun bind(viewBinding: ChatLeftYouBinding, position: Int) {
        viewBinding.userNameChatLeft.text=user.userName
        viewBinding.messageChatLeft.text=message
    }

    override fun getLayout(): Int {
        return R.layout.chat_left_you
    }

    override fun initializeViewBinding(view: View): ChatLeftYouBinding {
        return ChatLeftYouBinding.bind(view)
    }
}