package com.li.mykotlinapp.view.jav.activity

import android.content.Context
import android.content.Intent
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.jav.activity
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/4/25
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class JavMainActivity :BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_jav_main
    }

    override fun initData() {

    }

    companion object {
            fun start(content: Context){
                val intent = Intent(content, JavMainActivity().javaClass)
                content.startActivity(intent)
            }
        }
}