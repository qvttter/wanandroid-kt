package com.li.mykotlinapp.test;

import android.content.Context;
import android.content.Intent;

import com.li.mykotlinapp.base.BaseActivity;

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.test
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/8/17
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
public class TestXDCardFunctionActivity extends BaseActivity {
    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void initData() {

    }

    public static void start(Context context){
        Intent intent= new Intent(context,TestXDCardFunctionActivity.class);
        context.startActivity(intent);
    }

}
