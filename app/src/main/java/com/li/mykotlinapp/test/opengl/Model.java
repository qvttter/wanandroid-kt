package com.li.mykotlinapp.test.opengl;

import com.li.mykotlinapp.R;

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.test.opengl
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/5/7
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
public class Model {
    private static float r = 1.0f;

    public static int[] resIds = new int[] {
            R.raw.pic1, R.raw.pic2, R.raw.pic3,
            R.raw.pic4, R.raw.pic5, R.raw.pic6
    };

    public static float[] vertex = new float[] {
            //前面
            r, r, r,
            -r, r, r,
            -r, -r, r,
            r, -r, r,
            //后面
            r, r, -r,
            -r, r, -r,
            -r, -r, -r,
            r, -r, -r,
            //上面
            r, r, r,
            r, r, -r,
            -r, r, -r,
            -r, r, r,
            //下面
            r, -r, r,
            r, -r, -r,
            -r, -r, -r,
            -r, -r, r,
            //右面
            r, r, r,
            r, r, -r,
            r, -r, -r,
            r, -r, r,
            //左面
            -r, r, r,
            -r, r, -r,
            -r, -r, -r,
            -r, -r, r
    };

    public static float[] texture = new float[] {
            //前面
            0f, 0f,
            1f, 0f,
            1f, 1f,
            0f, 1f,
            //后面
            0f, 0f,
            1f, 0f,
            1f, 1f,
            0f, 1f,
            //上面
            0f, 0f,
            1f, 0f,
            1f, 1f,
            0f, 1f,
            //下面
            0f, 0f,
            1f, 0f,
            1f, 1f,
            0f, 1f,
            //右面
            0f, 0f,
            1f, 0f,
            1f, 1f,
            0f, 1f,
            //左面
            0f, 0f,
            1f, 0f,
            1f, 1f,
            0f, 1f
    };
}
