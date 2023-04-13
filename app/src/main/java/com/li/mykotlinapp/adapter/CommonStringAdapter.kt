package com.li.mykotlinapp.adapter

import androidx.annotation.Nullable
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.li.mykotlinapp.R
import com.li.mykotlinapp.bean.db.UserDbBean

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.adapter
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/7/10
 *************************************************************************/
class CommonStringAdapter(@Nullable data: List<UserDbBean>) : BaseQuickAdapter<UserDbBean, BaseViewHolder>(R.layout.item_common_string){
    override fun convert(holder: BaseViewHolder, item: UserDbBean) {
        holder.setText(R.id.tv_common,if (item==null) "" else item.age.toString()+item.name+item.number.toString() )
    }

}