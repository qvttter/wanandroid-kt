package com.li.mykotlinapp.adapter

import androidx.annotation.Nullable
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.li.mykotlinapp.R
import com.li.mykotlinapp.bean.db.UserDbBean

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.adapter
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/7/10
 *@Copyright:(C)2020 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class CommonStringAdapter(@Nullable data: List<UserDbBean>) : BaseQuickAdapter<UserDbBean, BaseViewHolder>(data){
    init {
        mLayoutResId = R.layout.item_common_string
    }
    override fun convert(helper: BaseViewHolder, item: UserDbBean?) {
        //item ?: (item!!.name + item!!.number.toString())
        helper.setText(R.id.tv_common,if (item==null) "" else item.age.toString()+item.name+item.number.toString() )
    }
}