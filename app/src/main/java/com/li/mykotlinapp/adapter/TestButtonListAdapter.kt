package com.li.mykotlinapp.adapter

import androidx.annotation.Nullable
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.li.mykotlinapp.R
import com.li.mykotlinapp.bean.TestButtonBean

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.adapter
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/1/10
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class TestButtonListAdapter(@Nullable data: MutableList<String>) :
    BaseQuickAdapter<String, BaseViewHolder>(
        R.layout.item_button
    ) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.btn_test, item)
    }
}