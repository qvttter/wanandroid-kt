package com.li.mykotlinapp.view

import android.content.Context
import android.content.Intent
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/9/30
 *@Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class LoginActivity : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun initData() {
        initEvent()
//        MaterialDialog(this)
//                .title(text = "提示")
//                .message(text = "内容")
//                .positiveButton(text = "确认",click = { materialDialog -> materialDialog.dismiss() })
//                .show()

    }

    private fun initEvent() {
//        btn_login.setOnClickListener { GuideActivity.start(mContext) }
        btn_login.setOnClickListener { MainActivity.start(mContext) }
    }

    companion object {
        fun start(content: Context) {
            val intent = Intent(content, LoginActivity().javaClass)
            content.startActivity(intent)
        }
    }
}