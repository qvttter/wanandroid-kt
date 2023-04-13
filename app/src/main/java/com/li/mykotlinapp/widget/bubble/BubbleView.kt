package com.li.mykotlinapp.widget.bubble

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.widget
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2023/4/13
 *@Copyright:(C)2023 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class BubbleView(context: Context, attrs: AttributeSet?) :RelativeLayout(context,attrs) {
    private var mContext:Context
    private var bubbleRight:BubbleRight

    init {
//        layoutDirection = LAYOUT_DIRECTION_LTR
//        addView(phoneView)
//        addView(cardView)
        mContext = context
        bubbleRight = BubbleRight(mContext)
        addRight()
    }

    private fun addRight(){
addView(bubbleRight)
    }

}