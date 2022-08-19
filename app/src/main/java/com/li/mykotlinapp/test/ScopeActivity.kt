package com.li.mykotlinapp.test

import android.content.Context
import android.content.Intent
import com.apkfuns.logutils.LogUtils
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity
import com.li.mykotlinapp.bean.UserBean

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.test
 *@Descriptions: Kotlin 作用域函数之let、with、run、also、apply
 *@Author: zhouli
 *@Date: 2022/7/1
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class ScopeActivity : BaseActivity() {


    override fun getLayout(): Int {
        return R.layout.activity_scope
    }

    override fun initData() {

        letScope()
    }

    //let 的使用
    fun letScope() {
        //let{}中可以使用it来代表intList对象
        var intList: List<Int> = arrayListOf(1, 2, 3)
        intList.let {
            LogUtils.e("it.size:" + it.size)
            LogUtils.e("it.lastIndex:" + it.lastIndex)
        }

        //通过?.let{}进行判断，如何为空，后面就不会再执行
        var list2: List<Int>? = null
        list2?.let {
            LogUtils.e("list2?.let ,it.size:" + it.size)
        }

        var user = UserBean("leo", 18)


        user.apply {
            name = "name1"
            age = 19
        }
    }

    companion object {
        fun start(content: Context) {
            val intent = Intent(content, ScopeActivity().javaClass)
            content.startActivity(intent)
        }
    }
}