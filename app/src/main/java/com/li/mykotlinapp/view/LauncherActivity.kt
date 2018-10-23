package com.li.mykotlinapp.view

import android.util.Log
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import java.util.logging.Logger

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class LauncherActivity : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.activity_launcher
    }

    override fun initData() {

        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ aLong ->
                    LoginActivity.start(mContext)
                    finish()
                }, { throwable -> Log.e("", "失败了！: " + throwable.message) })
    }
}
