package com.li.mykotlinapp.biz

import com.li.mykotlinapp.base.BaseBiz
import com.li.mykotlinapp.bean.BannerBean
import com.li.mykotlinapp.util.RxUtil
import io.reactivex.Observable

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.biz
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/10/23
 *@Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class CommonBiz : BaseBiz() {
    var service: Service

    init {
        service = retrofit.create(Service::class.java)
    }

    fun getMainBanner(): Observable<List<BannerBean>> {
        return service.getMainBanner()
                .compose(RxUtil.trans_io_main())
                .flatMap { t -> RxUtil.getData(t) }
    }
}