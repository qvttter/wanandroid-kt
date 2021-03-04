package com.li.mykotlinapp.view.vm

import androidx.lifecycle.MutableLiveData
import com.apkfuns.logutils.LogUtils
import com.easyway.ipu.base.BaseViewModel
import com.li.mykotlinapp.bean.db.UserDbBean
import com.li.mykotlinapp.biz.db.DbBiz

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.vm
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/7/9
 *@Copyright:(C)2020 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class ObjectVM : BaseViewModel() {
    val users = MutableLiveData<List<UserDbBean>>()

    fun add(user: UserDbBean) {
        launch {
            DbBiz.getInstance().addOrChangeUser(user)
            val dbBeans = runIO {
                DbBiz.getInstance().getAllUser()
            }
            users.value =dbBeans
            message.value = "添加成功"
        }
    }

    fun change(user: UserDbBean) {
        launch {
            DbBiz.getInstance().addOrChangeUser(user)
            val dbBeans = runIO {
                DbBiz.getInstance().getAllUser()
            }
            users.value =dbBeans
        }
    }

    fun searchAll() {
        launch {
            val dbBeans = runIO {
                DbBiz.getInstance().getAllUser()
            }
            users.value =dbBeans
            message.value = "查找成功"
            LogUtils.e("ObjectVM 查找成功")
        }
    }

    fun delete(user: UserDbBean){
        launch {
            DbBiz.getInstance().deleteUser(user)
            val dbBeans = runIO {
                DbBiz.getInstance().getAllUser()
            }
            users.value =dbBeans
        }
    }

    fun deleteAll() {
        launch {
            DbBiz.getInstance().deleteAllUser()
            val dbBeans = runIO {
                DbBiz.getInstance().getAllUser()
            }
            users.value =dbBeans
            message.value = "删除成功"
            LogUtils.e("ObjectVM 删除成功")
        }
    }


}