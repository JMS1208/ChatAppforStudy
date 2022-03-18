package com.jms.a20220315_mychattingapp02.Adapter

import com.jms.a20220315_mychattingapp02.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class UserItem: Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

    }

    override fun getLayout(): Int {
        return R.layout.chat_item_list
    }

}