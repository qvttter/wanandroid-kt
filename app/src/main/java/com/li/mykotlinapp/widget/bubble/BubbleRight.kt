package com.li.mykotlinapp.widget.bubble

import android.content.Context
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.li.mykotlinapp.R

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.widget.bubble
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2023/4/13
 *@Copyright:(C)2023 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class BubbleRight(context: Context) : View(context) {
    private var paint: Paint
    private var centerX = 0f
    private var centerY = 0f
    private var bodyWidth = 0f
    private var bodyHeight = 0f
    private var bodyCorner = 60f
    private var leftPadding = 60f

    init {
        paint = Paint()
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBody(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = (w / 2).toFloat()
        centerY = (h / 2).toFloat()
        bodyWidth = w-leftPadding
        bodyHeight = h.toFloat()
    }

    private fun drawBody(canvas: Canvas){
        paint.color = resources.getColor(R.color.color_red)
        canvas.drawRoundRect(centerX - bodyWidth / 2, 0f,
            centerX + bodyWidth / 2,  bodyHeight, bodyCorner, bodyCorner, paint)

        //左下角大矩形
        canvas.drawRect(centerX - bodyWidth / 2, centerY, bodyCorner+leftPadding, bodyHeight, paint)

        //左下角扇形
        canvas.drawArc(centerX - bodyWidth / 2 - 20, bodyHeight-30, centerX - bodyWidth / 2 + 20, bodyHeight, 90f, 90f, true, paint)

        //左下角矩形
        canvas.drawRect(centerX - bodyWidth / 2 - 20, bodyHeight-30, centerX - bodyWidth / 2, bodyHeight-15, paint)
        paint.color = resources.getColor(R.color.white)
        canvas.drawArc(centerX - bodyWidth / 2 - 40, bodyHeight-55, centerX - bodyWidth / 2 , bodyHeight-15, 0f, 90f, true, paint)

    }

}