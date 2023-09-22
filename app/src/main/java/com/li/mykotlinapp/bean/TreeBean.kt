package com.li.mykotlinapp.bean

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.bean
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2023/6/15
 *@Copyright:(C)2023 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
data class TreeBean(
    val children: List<Any>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val visible: Int
)