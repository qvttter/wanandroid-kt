package com.li.mykotlinapp.test

import android.content.Context
import android.content.Intent
import com.li.mykotlinapp.base.BaseActivity

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.test
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2023/2/17
 *@Copyright:(C)2023 . All rights reserved.
 *************************************************************************/
class TestAllScreenActivity :BaseActivity() {
    override fun getLayout(): Int {
        TODO("Not yet implemented")
    }

    override fun initData() {
        TODO("Not yet implemented")
    }

    companion object {
            fun start(content: Context){
                val intent = Intent(content, TestAllScreenActivity().javaClass)
                content.startActivity(intent)
            }
        }
}