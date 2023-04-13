package com.li.mykotlinapp.bean

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.bean
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/5/16
 *@Copyright:(C)2022 . All rights reserved.
 *************************************************************************/
data class MQTTProvisionResponse(
    val credentialsType: String,
    val credentialsValue: String,
    val status: String
)