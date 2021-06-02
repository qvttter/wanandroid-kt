package com.li.mykotlinapp.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.li.mykotlinapp.R

/************************************************************************
 * @Project: MyKotlinApp
 * @Package_Name: com.li.mykotlinapp.adapter
 * @Descriptions:
 * @Author: zhouli
 * @Date: 2018/10/23
 * @Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 */
class CommonImgAdapter(@Nullable data: List<String>) : BaseQuickAdapter<String, BaseViewHolder>(data) {

    init {
        mLayoutResId = R.layout.item_common_img
    }

    override fun convert(helper: BaseViewHolder, item: String) {
        Glide.with(mContext)
                .load(item)
                .into(helper.getView(R.id.iv_common))
    }
}