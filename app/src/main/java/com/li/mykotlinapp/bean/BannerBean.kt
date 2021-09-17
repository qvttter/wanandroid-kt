package com.li.mykotlinapp.bean

import com.stx.xhb.androidx.entity.BaseBannerInfo

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.bean
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/10/23
 *@Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/

data class BannerBean(
        val desc: String,
        val id: Int,
        val imagePath: String,
        val isVisible: Int,
        val order: Int,
        val title: String,
        val type: Int,
        val url: String
): BaseBannerInfo {
    override fun toString(): String {
        return imagePath
    }

    override fun getXBannerUrl(): Any {
        return imagePath
    }

    override fun getXBannerTitle(): String {
        return title
    }
}