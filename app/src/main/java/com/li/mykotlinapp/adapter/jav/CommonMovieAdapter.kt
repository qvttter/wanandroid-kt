package com.li.mykotlinapp.adapter.jav

import android.widget.ImageView
import androidx.annotation.Nullable
import com.apkfuns.logutils.LogUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.li.mykotlinapp.R
import com.li.mykotlinapp.bean.jav.JavItemBean

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.adapter.jav
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/4/26
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class CommonMovieAdapter(@Nullable data: MutableList<JavItemBean>) : BaseQuickAdapter<JavItemBean, BaseViewHolder>(R.layout.item_jav_movie, data) {

    override fun convert(holder: BaseViewHolder, item: JavItemBean) {
        var ivCover = holder.getView<ImageView>(R.id.iv_cover)
        Glide.with(context)
            .load(item.img)
            .into(ivCover)

        holder.setText(R.id.tv_title,item.title)
        holder.setText(R.id.tv_code,item.code)
        holder.setText(R.id.tv_time,item.time)

//        holder.itemView.setOnClickListener(View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, JavbusDetailActivity.class);
//                intent.putExtra(URL,entity.getUrl());
//                context.startActivity(intent);
//            }
//        });

    }

}