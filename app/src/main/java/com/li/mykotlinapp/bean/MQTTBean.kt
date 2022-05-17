package com.li.mykotlinapp.bean

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.bean
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/5/13
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
data class MQTTBean (
    var method:String,
    var params:Params
        ){

    data class Params(var url:String){

    }

}