package com.li.mykotlinapp.view;

import com.li.mykotlinapp.base.BaseActivity;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/10/19 
 *@Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved. 
 *************************************************************************/
public class TestActivity extends BaseActivity {
    public static final String BASE_URL = "http://www.wanandroid.com/";

    MZBannerView mzBannerView;

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void initData() {
        List<String> mList = new ArrayList<>();
        mList.add("123");
        mzBannerView.setPages(mList, new MZHolderCreator() {
            @Override
            public MZViewHolder createViewHolder() {
                return null;
            }
        });
    }
}
