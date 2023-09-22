package com.li.mykotlinapp.bean

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.bean.response
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2023/6/15
 *@Copyright:(C)2023 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
data class CommonWebsiteBean(
    val category: String,
    val icon: String,
    val id: Int,
    val link: String,
    val name: String,
    val order: Int,
    val visible: Int
)