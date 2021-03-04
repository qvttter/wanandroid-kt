package com.li.mykotlinapp.adapter

import androidx.annotation.Nullable
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.li.mykotlinapp.R
import com.li.mykotlinapp.bean.ExcelFileBean
import com.li.mykotlinapp.bean.ExcelItemBean

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.adapter
 *@Descriptions: ExcelFileBean
 *@Author: zhouli
 *@Date: 2020/10/20
 *@Copyright:(C)2020 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class ExcelFileAdapter (@Nullable data: List<ExcelFileBean>) : BaseQuickAdapter<ExcelFileBean, BaseViewHolder>(data) {
    init {
        mLayoutResId = R.layout.item_excel_file
    }
    override fun convert(helper: BaseViewHolder, item: ExcelFileBean) {
        helper.setText(R.id.tv_name,item.name)

    }
}