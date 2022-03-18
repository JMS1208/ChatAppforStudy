package com.jms.a20220315_mychattingapp02.Adapter

import android.view.View
import com.jms.a20220315_mychattingapp02.Model.User
import com.jms.a20220315_mychattingapp02.R
import com.jms.a20220315_mychattingapp02.databinding.ChatItemListBinding
import com.xwray.groupie.viewbinding.BindableItem

class UserItemBinding(val user: User)
    :BindableItem<ChatItemListBinding>(){
    override fun bind(viewBinding: ChatItemListBinding, position: Int) {
        viewBinding.nameTextItem.text=user.userName
        viewBinding.messageTextItem.text=user.uid

    }

    override fun getLayout(): Int {
        return R.layout.chat_item_list
    }

    override fun initializeViewBinding(view: View): ChatItemListBinding {
        return ChatItemListBinding.bind(view)
    }

}