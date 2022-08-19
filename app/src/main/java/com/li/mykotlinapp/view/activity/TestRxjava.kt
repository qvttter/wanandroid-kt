package com.li.mykotlinapp.view.activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import com.li.mykotlinapp.base.BaseVMActivity
import com.li.mykotlinapp.databinding.ActivityTestRxjavaBinding
import com.li.mykotlinapp.view.vm.TestRxJavaVM

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.activity
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/8/12
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class TestRxjava :BaseVMActivity<TestRxJavaVM,ActivityTestRxjavaBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityTestRxjavaBinding
        get() = ActivityTestRxjavaBinding::inflate

    override fun initData() {

        binding.btnDoonnext.setOnClickListener {

        }


    }

    companion object {
            fun start(content: Context){
                val intent = Intent(content, TestRxjava().javaClass)
                content.startActivity(intent)
            }
        }
}