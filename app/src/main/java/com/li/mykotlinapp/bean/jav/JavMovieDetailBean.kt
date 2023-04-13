package com.li.mykotlinapp.bean.jav

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.bean.jav
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/4/27
 *@Copyright:(C)2022 . All rights reserved.
 *************************************************************************/
data class JavMovieDetailBean(
    var title: String,
    var mainImg: String,
    var movieCode: String,
    var date: String,
    var time: String,
    var director: String,
    var company: String,
    var publisher: String
) {

}
