package com.li.mykotlinapp.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.li.mykotlinapp.R

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.widget
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/8/25
 *@Copyright:(C)2020 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class EMVCardView (context: Context, attrs: AttributeSet?) : View(context, attrs){
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var centerX = 0.0f
    private var centerY = 0.0f
    private var oneP = 0.0f

    init {
        paint.style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = (w / 2).toFloat()
        centerY = h / 2.toFloat()
        oneP = w / 9.toFloat()
//        centerPhoneX = centerX - 4 * oneP
//        centerPhoneY = centerY

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //画卡
        paint.color = context.getColor(R.color.colorAccent)
        canvas.drawRoundRect(
                centerX + 0.2f * oneP,
                centerY + 2.3f * oneP,
                centerX + 8.7f * oneP,
                centerY - 3.5f * oneP,
                oneP / 2 * 0.4f,
                oneP / 2 * 0.4f, paint)



        //画卡上的芯片
        paint.color = context.getColor(R.color.col_golden)
        canvas.drawRoundRect(
                centerX + 0.9f * oneP,
                centerY + 0.2f * oneP,
                centerX + 2.2f * oneP,
                centerY - 0.9f * oneP,
                oneP / 2 * 0.5f,
                oneP / 2 * 0.5f, paint)

    }
}