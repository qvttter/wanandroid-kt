package com.li.mykotlinapp.biz.db

import com.li.mykotlinapp.bean.db.UserDbBean
import io.objectbox.Box
import io.objectbox.BoxStore

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.biz.db
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/7/8
 *@Copyright:(C)2020 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class DbBiz private constructor() {
    private lateinit var boxStore: BoxStore
    private lateinit var userDb: Box<UserDbBean>

    init {
        boxStore = ObjectBox.boxStore
        userDb = boxStore.boxFor(UserDbBean::class.java)
    }

    companion object {
        fun getInstance() = Holder.INSTANCE
    }

    private object Holder {
        val INSTANCE = DbBiz()
    }

    fun addOrChangeUser(user: UserDbBean) {
        userDb.put(user)
    }

    fun getAllUser(): List<UserDbBean> {
        return userDb.all
    }

    fun deleteUser(user: UserDbBean){
        userDb.remove(user)
    }

    fun deleteAllUser(){
        userDb.removeAll()
    }

}