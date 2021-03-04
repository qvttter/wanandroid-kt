package com.li.mykotlinapp.view.activity

import android.content.Context
import android.content.Intent
import com.li.mykotlinapp.R
import com.li.mykotlinapp.adapter.GuidePageAdapter
import com.li.mykotlinapp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_guide.*
import com.li.mykotlinapp.widget.ZoomOutPageTransformer


/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/10/18
 *@Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class GuideActivity : BaseActivity() {

    lateinit var imgPageAdapter: GuidePageAdapter
    val list = arrayOf("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1539919029&di=d2de815f8f68cf701c1d28a32730e070&src=http://b-ssl.duitang.com/uploads/item/201511/07/20151107231726_FsGWd.jpeg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539929115335&di=d263170750b984b9ddd1d41326c1ee89&imgtype=0&src=http%3A%2F%2Fcdnq.duitang.com%2Fuploads%2Fitem%2F201504%2F27%2F20150427181438_WFKeC.thumb.700_0.jpeg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539929115335&di=794850477a91c6242fbd2ffbe3f07446&imgtype=0&src=http%3A%2F%2Fwww.4gbizhi.com%2Fuploads%2Fallimg%2F150316%2F144Ha0M-0.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539929115335&di=fe7fc7c200069a2bacbe0465a171d652&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01009959e1da10a80121ae0c2fd0f0.jpg%402o.jpg")

    override fun getLayout(): Int {
        return R.layout.activity_guide
    }

    override fun initData() {
        imgPageAdapter = GuidePageAdapter(mContext, list)

        vp_guide.adapter = imgPageAdapter
        vp_guide.offscreenPageLimit = 4
        vp_guide.setPageTransformer(true, ZoomOutPageTransformer())
        initEvent()
    }

    fun initEvent() {
        //将父布局的touch事件传递给viewpager，解决触摸滑动ViewPager左右两边的页面无反应的问题
        ll_guide_main.setOnTouchListener { _, event -> vp_guide.dispatchTouchEvent(event) }
    }

    companion object {
        fun start(content: Context) {
            val intent = Intent(content, GuideActivity().javaClass)
            content.startActivity(intent)
        }
    }
}