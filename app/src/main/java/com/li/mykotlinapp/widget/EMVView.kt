package com.li.mykotlinapp.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.widget
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/8/25
 *@Copyright:(C)2020 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class EMVView (context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs){
    private var phoneView = EMVPhoneView(context,attrs)
    private var cardView = EMVCardView(context,attrs)

    init {
        layoutDirection = LAYOUT_DIRECTION_LTR
        addView(phoneView)
        addView(cardView)

    }

//    private fun addPhone() {
//        lockBodyView = LockBodyView(mContext)
//        addView(lockBodyView)
//    }
//
//    private fun addCard() {
//        lockHeadView = LockHeadView(mContext)
//        addView(lockHeadView)
//    }

}