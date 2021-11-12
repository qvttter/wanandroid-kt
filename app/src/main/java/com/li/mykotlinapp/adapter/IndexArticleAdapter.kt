package com.li.mykotlinapp.adapter

import androidx.annotation.Nullable
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.li.mykotlinapp.R
import com.li.mykotlinapp.bean.ArticleBean
import com.li.mykotlinapp.util.TimeUtil
import com.li.mykotlinapp.util.TimeUtils

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.adapter
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/11/7
 *@Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class IndexArticleAdapter(@Nullable data: MutableList<ArticleBean>) :
    BaseQuickAdapter<ArticleBean, BaseViewHolder>(R.layout.item_index_article, data),
    LoadMoreModule {
    override fun convert(helper: BaseViewHolder, item: ArticleBean) {
        helper.setText(R.id.tv_index_article_title, item.title)
        if (item.author.isEmpty()) {
            helper.setText(R.id.tv_index_article_user, "分享人：" + item.shareUser)
        } else {
            helper.setText(R.id.tv_index_article_user, "作者：" + item.author)
        }
        helper.setText(R.id.tv_time, TimeUtil.timeMillisToTime(item.publishTime))
    }
}