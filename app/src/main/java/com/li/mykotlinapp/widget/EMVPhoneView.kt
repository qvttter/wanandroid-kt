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
class EMVPhoneView (context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var centerX = 0.0f
    private var centerY = 0.0f
    private var oneP = 0.0f
    private var centerPhoneX = 0.0f
    private var centerPhoneY = 0.0f

    init {
        paint.style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = (w / 2).toFloat()
        centerY = h / 2.toFloat()
        oneP = w / 10.toFloat()
        centerPhoneX = centerX
        centerPhoneY = centerY

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //画手机的主体
        paint.color = Color.BLACK
        canvas.drawRoundRect(
                centerPhoneX - 4 * oneP,
                centerPhoneY + 7.5f * oneP,
                centerPhoneX + 4 * oneP,
                centerPhoneY - 7.5f * oneP,
                oneP / 2,
                oneP / 2,
                paint
        )
        //画屏幕
        paint.color = Color.WHITE
        canvas.drawRect(centerPhoneX - 3.5f * oneP,
                centerPhoneY + 6f * oneP,
                centerPhoneX + 3.5f * oneP,
                centerPhoneY - 6f * oneP,
                paint)

        //画侧条
        paint.color = context.getColor(R.color.color_light_grey)
        paint.strokeWidth = oneP * 0.1f
        canvas.drawLine(
                centerX - 0.2f * oneP,
                centerY - 3.5f * oneP,
                centerX - 0.2f * oneP,
                centerY + 2.3f * oneP, paint)

    }
}