package com.li.mykotlinapp.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseComposeActivity :AppCompatActivity() {
    protected lateinit var mContext: Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        initData()
    }

    abstract fun initData()

}