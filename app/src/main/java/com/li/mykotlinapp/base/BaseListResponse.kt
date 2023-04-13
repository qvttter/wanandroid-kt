package com.li.mykotlinapp.base

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.base
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/10/23
 
 *************************************************************************/

data class BaseListResponse<T>(
    val data: List<T>,
    val errorCode: Int,
    val errorMsg: String
)