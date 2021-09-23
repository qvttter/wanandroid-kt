package com.li.mykotlinapp.view.activity

import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity
import com.li.mykotlinapp.bean.LoginResponse
import com.li.mykotlinapp.biz.CommonBiz
import com.li.mykotlinapp.util.PrefUtil
import com.li.mykotlinapp.util.RxUtil

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class LauncherActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_launcher
    }

    override fun initData() {
        if (PrefUtil.flagLogin) {
//            CommonBiz.getInstance().login(PrefUtil.userName!!, PrefUtil.pwd!!)
//                    .compose(RxUtil.trans_io_main())
//                    .subscribe({ t: LoginResponse? ->
//                        MainActivity.start(mContext)
//                        finish()
//                    }, { t: Throwable? -> shortToast(t!!.message) })
        } else {
            LoginActivity.start(mContext)
            finish()
        }
    }
}
