package com.li.mykotlinapp.view.activity;

import android.widget.Button;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.li.mykotlinapp.R;
import com.li.mykotlinapp.base.BaseActivity;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/10/19 
  
 *************************************************************************/
public class TestJavaActivity extends BaseActivity {

    MZBannerView mzBannerView;

    @Override
    public int getLayout() {
        return R.layout.activity_test;
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

        RecyclerView rcv = new RecyclerView(mContext);
        rcv.setLayoutManager(new LinearLayoutManager(mContext));

        List<Button> bList = new ArrayList<>();
        printButtonText(bList);
    }

    private void printButtonText(List<? extends TextView> textViews){
        for (TextView t:textViews) {
            LogUtils.e(t.getText().toString());
        }
    }
}
