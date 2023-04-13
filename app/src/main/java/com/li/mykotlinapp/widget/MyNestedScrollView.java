package com.li.mykotlinapp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.core.widget.NestedScrollView;

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.widget
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/11/8
 *@Copyright:(C)2022 . All rights reserved.
 *************************************************************************/
public class MyNestedScrollView extends NestedScrollView {
    private int downX;
    private int downY;
    private int moveX;
    private int moveY;
    private int mTouchSlop;

    public MyNestedScrollView(Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public MyNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getRawX();
                downY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = (int) e.getRawY();
                moveX = (int) e.getRawX();
                if (Math.abs(moveX - downX) > mTouchSlop) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(e);
    }
}
