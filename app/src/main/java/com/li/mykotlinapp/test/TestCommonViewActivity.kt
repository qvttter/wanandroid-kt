package com.li.mykotlinapp.test

import android.content.Context
import android.content.Intent
import android.view.View
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.test
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2021/11/3
 *@Copyright:(C)2021 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class TestCommonViewActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_motion_demo
    }

    override fun initData() {
    }


    companion object {
        fun start(content: Context) {
            val intent = Intent(content, TestCommonViewActivity().javaClass)
            content.startActivity(intent)
        }
    }
}