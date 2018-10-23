package com.li.mykotlinapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_image.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/10/18
 *@Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class ImageFragment : BaseFragment() {
    val IMG_URL = "img_url"


    lateinit var imgUrl:String

    override fun getLayout(): Int {
        return R.layout.fragment_image
    }

    override fun initData() {
        imgUrl = arguments!!.getString(IMG_URL)
        Glide.with(mContext)
                .load(imgUrl)
                .into(iv_page)
    }

    fun newInstance(imgUrl: String): Fragment {
        var fragment = ImageFragment();
        val bundle = Bundle()
        bundle.putString(IMG_URL, imgUrl)
        fragment.arguments = bundle
        return fragment
    }
}