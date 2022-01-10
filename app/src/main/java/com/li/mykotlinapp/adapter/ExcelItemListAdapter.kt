package com.li.mykotlinapp.adapter

import androidx.annotation.Nullable
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.li.mykotlinapp.R
import com.li.mykotlinapp.bean.ExcelItemBean

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.adapter
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/10/20
 *@Copyright:(C)2020 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class ExcelItemListAdapter(@Nullable data: List<ExcelItemBean>) :
    BaseQuickAdapter<ExcelItemBean, BaseViewHolder>(R.layout.item_excel_title) {
    override fun convert(helper: BaseViewHolder, item: ExcelItemBean) {
        helper.setText(R.id.tv_name, item.name)
        helper.setText(R.id.tv_age, item.age)
        helper.setText(R.id.tv_sex, item.sex)

    }

}