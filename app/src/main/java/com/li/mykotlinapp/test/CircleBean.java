package com.li.mykotlinapp.test;

import android.graphics.PointF;

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.test
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2023/9/21
 *@Copyright:(C)2023 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
public class CircleBean {
    private PointF pointF;
    private int type;

    public CircleBean(PointF pointF, int type) {
        this.pointF = pointF;
        this.type = type;
    }

    public PointF getPointF() {
        return pointF;
    }

    public void setPointF(PointF pointF) {
        this.pointF = pointF;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
