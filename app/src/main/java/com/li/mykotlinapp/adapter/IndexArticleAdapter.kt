package com.li.mykotlinapp.adapter

import androidx.annotation.Nullable
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.li.mykotlinapp.R
import com.li.mykotlinapp.bean.ArticleBean

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.adapter
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/11/7
 *@Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class IndexArticleAdapter(@Nullable data: List<ArticleBean>) : BaseQuickAdapter<ArticleBean, BaseViewHolder>(data) {

    init {
        mLayoutResId = R.layout.item_index_article
    }
    override fun convert(helper: BaseViewHolder, item: ArticleBean) {
        helper.setText(R.id.tv_index_article_title,item.title)
        helper.setText(R.id.tv_index_article_user,item.author)

    }
}