package com.li.mykotlinapp.view

import android.content.Context
import android.content.Intent
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity
import com.li.mykotlinapp.bean.BannerBean
import com.li.mykotlinapp.biz.CommonBiz
import com.zhouwei.mzbanner.holder.MZHolderCreator
import com.zhouwei.mzbanner.holder.MZViewHolder
import kotlinx.android.synthetic.main.activity_main.*
import com.li.mykotlinapp.common.GlideImageLoader
import com.zhouwei.mzbanner.MZBannerView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlin.collections.ArrayList
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb




class MainActivity : BaseActivity() {
    var biz = CommonBiz()
    var list: MutableList<BannerBean> = ArrayList()
    var imgList: MutableList<String> = ArrayList()

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
//        list.add("http://b-ssl.duitang.com/uploads/item/201511/07/20151107231726_FsGWd.jpeg")
//        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539929115335&di=d263170750b984b9ddd1d41326c1ee89&imgtype=0&src=http%3A%2F%2Fcdnq.duitang.com%2Fuploads%2Fitem%2F201504%2F27%2F20150427181438_WFKeC.thumb.700_0.jpeg")
//        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539929115335&di=794850477a91c6242fbd2ffbe3f07446&imgtype=0&src=http%3A%2F%2Fwww.4gbizhi.com%2Fuploads%2Fallimg%2F150316%2F144Ha0M-0.jpg")
//        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539929115335&di=fe7fc7c200069a2bacbe0465a171d652&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01009959e1da10a80121ae0c2fd0f0.jpg%402o.jpg")

        main_banner.setImageLoader(GlideImageLoader())
        main_banner.setDelayTime(4000)
        biz.getMainBanner()
                .subscribe(object: Observer<List<BannerBean>?> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: List<BannerBean>) {
                        list = t as MutableList<BannerBean>
                        for (bean in list){
                            imgList.add(bean.imagePath)
                        }
                        main_banner.setImages(imgList)
                        main_banner.start()
                    }

                    override fun onError(e: Throwable) {
                        shortToast("轮播出错了")
                    }
                })

        initEvent()
    }

    private fun initEvent() {
        main_banner.setOnBannerListener { position: Int ->
            CommonWebViewActivity.start(mContext, list[position].url)
        }
    }


    companion object {
        fun start(content: Context) {
            val intent = Intent(content, MainActivity().javaClass)
            content.startActivity(intent)
        }
    }
}
