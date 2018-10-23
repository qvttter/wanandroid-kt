package com.li.mykotlinapp.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.li.mykotlinapp.R


/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.adapter
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/10/18
 *@Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class GuidePageAdapter : PagerAdapter {

    var content: Context
    private var list: Array<String>

    constructor(content: Context, list: Array<String>) : super() {
        this.content = content
        this.list = list
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var view = View.inflate(content, R.layout.fragment_image,null)
        var ivPage = view.findViewById<ImageView>(R.id.iv_page)
        Glide.with(content)
                .load(list[position])
                .into(ivPage)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        super.destroyItem(container, position, `object`)
        container.removeView(`object` as View?)
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object`
    }

    override fun getCount(): Int {
        return list.size
    }
}