package com.li.mykotlinapp.view.activity

import android.content.Context
import android.content.Intent
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity
import com.li.mykotlinapp.bean.LoginResponse
import com.li.mykotlinapp.biz.CommonBiz
import com.li.mykotlinapp.util.PrefUtil
import com.li.mykotlinapp.util.RxUtil
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
        PrefUtil.flagLogin = false
        et_username.setText(PrefUtil.userName)
        et_password.setText(PrefUtil.pwd)
        initEvent()
    }

    private fun initEvent() {
        btn_login.setOnClickListener {
            val username = et_username.text.toString()
            val password = et_password.text.toString()
//            CommonBiz.getInstance().login(username, password)
//                    .compose(RxUtil.trans_io_main())
//                    .subscribe({ t: LoginResponse? ->
//                        PrefUtil.flagLogin = true
//                        PrefUtil.userName = username
//                        PrefUtil.pwd = password
//                        MainActivity.start(mContext)
//                    }, { t: Throwable? -> shortToast(t!!.message) })
        }

        tv_title.setOnClickListener {
            TestKtActivity.start(mContext)
        }
    }

    companion object {
        fun start(content: Context) {
            val intent = Intent(content, LoginActivity().javaClass)
            content.startActivity(intent)
        }
    }
}