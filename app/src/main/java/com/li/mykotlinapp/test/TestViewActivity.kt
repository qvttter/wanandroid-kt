package com.li.mykotlinapp.test

import android.content.Context
import android.content.Intent
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.test
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/8/25
 *@Copyright:(C)2020 . All rights reserved.
 *************************************************************************/
class TestViewActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_test_view
    }

    override fun initData() {
    }

    companion object {
        fun start(content: Context) {
            val intent = Intent(content, TestViewActivity().javaClass)
            content.startActivity(intent)
        }
    }
}