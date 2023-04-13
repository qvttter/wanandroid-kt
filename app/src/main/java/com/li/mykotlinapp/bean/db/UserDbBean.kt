package com.li.mykotlinapp.bean.db

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.bean.db
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/7/8
 *@Copyright:(C)2020 . All rights reserved.
 *************************************************************************/
@Entity
data class UserDbBean(
        @Id
        var id: Long = 0,
        var age: Int = 0,
        var name: String,
        var number: Long
) {
}