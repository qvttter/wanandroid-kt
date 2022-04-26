package com.li.mykotlinapp.bean.jav

import java.io.Serializable

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.bean.jav
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/4/26
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
data class JavItemBean(
    var url: String,
    var img: String,
    var title: String,
    var code: String,
    var time: String
) :Serializable{
}