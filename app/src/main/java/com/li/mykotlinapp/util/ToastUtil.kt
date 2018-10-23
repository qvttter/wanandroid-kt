package com.li.mykotlinapp.util

import android.content.Context
import android.widget.Toast

/************************************************************************
 * @Project: MyKotlinApp
 * @Package_Name: com.li.mykotlinapp.util
 * @Descriptions:
 * @Author: zhouli
 * @Date: 2018/10/8
 * @Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 */
object ToastUtil {
    //    public static void toastPicture(Context context, int id) {
    //        Toast mToast = Toast.makeText(context, "", Toast.LENGTH_LONG);
    //        mToast.setGravity(Gravity.CENTER, 0, 0);
    //        LinearLayout toastView = (LinearLayout) mToast.getView();
    //        toastView.setBackgroundColor(0x00000000);
    //        toastView.setOrientation(LinearLayout.HORIZONTAL);
    //        ImageView imageView = new ImageView(context);
    //        imageView.setImageResource(id);
    //        toastView.addView(imageView, 0);
    //        mToast.show();
    //    }

    private var mToast: Toast? = null

    fun longToast(context: Context, msg: String) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
        } else {
            mToast!!.setText(msg)
            mToast!!.duration = Toast.LENGTH_LONG
        }
        mToast!!.show()
    }

    fun shortToast(context: Context, msg: String) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        } else {
            mToast!!.setText(msg)
            mToast!!.duration = Toast.LENGTH_SHORT
        }
        mToast!!.show()
    }

    fun toastNetError(context: Context?) {
        if (context == null) return
        shortToast(context, "连接失败，请稍后再试")
    }

}
