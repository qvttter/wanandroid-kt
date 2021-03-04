package com.li.mykotlinapp.widget

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import com.li.mykotlinapp.R

/************************************************************************
 *@Project: ipu_android
 *@Package_Name: com.easyway.ipu.ui.widget
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/8/25
 *@Copyright:(C)2020 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class EMVScanView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private var centerX = 0.0f
    private var centerY = 0.0f
    private var oneP = 0.0f
    private var centerPhoneX = 0.0f
    private var centerPhoneY = 0.0f
    private var progress = 0f

    fun getProgress(): Float {
        return progress
    }

    fun setProgress(progress: Float) {
        this.progress = progress
        invalidate()
    }

    private var animator: ObjectAnimator

    init {
        paint.style = Paint.Style.FILL
        animator = ObjectAnimator.ofFloat(this, "progress", 0f, 4f)
        animator.duration = 2000
//        animator.interpolator = AccelerateInterpolator()
        animator.repeatMode = ValueAnimator.RESTART
        animator.repeatCount = ValueAnimator.INFINITE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = (w / 2).toFloat()
        centerY = h / 2.toFloat()
        oneP = w / 18.toFloat()
        centerPhoneX = centerX - 4 * oneP
        centerPhoneY = centerY
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

        //画箭头
        paint.color = context.getColor(R.color.colorDarkBlue)
        paint.strokeWidth = oneP * 0.1f
        var bitmap = BitmapFactory.decodeResource(context.resources,
                R.drawable.left)
        canvas.drawBitmap(bitmap,centerX + 5.2f * oneP - progress * oneP, centerY - 7f * oneP,paint)
    }

    fun start(){
        animator.start()

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> start()
            MotionEvent.ACTION_UP -> {
            }
            MotionEvent.ACTION_MOVE -> {
            }
        }
        return super.onTouchEvent(event)
    }
}