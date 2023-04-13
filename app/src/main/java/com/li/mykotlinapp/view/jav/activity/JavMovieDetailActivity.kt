package com.li.mykotlinapp.view.jav.activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.apkfuns.logutils.LogUtils
import com.bumptech.glide.Glide
import com.li.mykotlinapp.base.BaseVMActivity
import com.li.mykotlinapp.databinding.ActivityJavMainBinding
import com.li.mykotlinapp.databinding.ActivityJavMovieDetailBinding
import com.li.mykotlinapp.view.vm.JavVM
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.jav.activity
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/4/27
 *@Copyright:(C)2022 . All rights reserved.
 *************************************************************************/
class JavMovieDetailActivity : BaseVMActivity<JavVM, ActivityJavMovieDetailBinding>() {
    private var movieCode=""

    override val bindingInflater: (LayoutInflater) -> ActivityJavMovieDetailBinding
        get() = ActivityJavMovieDetailBinding::inflate

    override fun initData() {
        lifecycleScope.launch {
            mViewModel.movieDetailValue.collect {
                if (it != null) {
                    binding.tvTitle.text = it.title

                    Glide.with(mContext)
                        .load(it.mainImg)
                        .into(binding.ivMainImg)

                    binding.tvMovieCode.text = it.movieCode
                    binding.tvTime.text = it.time
                    binding.tvTitle.text = it.title
                    binding.tvTitle.text = it.title


                }
            }
        }

        movieCode = intent.getStringExtra(MOVIE_CODE)?:""
        LogUtils.e("番号:"+movieCode)
        if (movieCode.isNotEmpty()){
            mViewModel.getMovieDetail(movieCode)
        }

    }

    companion object {
        const val MOVIE_CODE = "movie_code"
        fun start(content: Context,movieCode:String) {
            val intent = Intent(content, JavMovieDetailActivity().javaClass)
            intent.putExtra(MOVIE_CODE,movieCode)
            content.startActivity(intent)
        }
    }

}