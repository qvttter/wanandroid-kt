package com.li.mykotlinapp.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.apkfuns.logutils.LogUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Locale;

/************************************************************************
 *@Project: android
 *@Package_Name: io.ionic.starter.utils
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/1/6
 *@Copyright:(C)2022 . All rights reserved.
 *************************************************************************/
public class RxTools {
    public static boolean installAppSilent(Context context,String filePath) {
        File file = RxFileTool.getFileByPath(filePath);
        if (!RxFileTool.isFileExists(file)) return false;
        String command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install $filePath";
//        RxShellTool.CommandResult commandResult = RxShellTool.execCmd(command, !isSystemApp(context), true);
        RxShellTool.CommandResult commandResult = RxShellTool.execCmd(command, true, true);
        return commandResult.successMsg != null && commandResult.successMsg.toLowerCase(Locale.ROOT).contains("success");
    }

    public static void openApp(String pkgName,String pkgNameClass){
        String command = "am start -n "+pkgName+"/"+pkgNameClass;
        RxShellTool.CommandResult commandResult = RxShellTool.execCmd(command, true, true);
    }

    /**
     * 静默安装
     *
     * @param apkUrl apk本地路径
     * @return
     */
    public static boolean silenceInstall(String apkUrl) {
        boolean result =false;
        DataOutputStream dataOutputStream =null;
        BufferedReader errorStream =null;
        try {
            Process process =Runtime.getRuntime().exec("su");
            dataOutputStream =new DataOutputStream(process.getOutputStream());
            String command ="pm install -r " + apkUrl +"\n";
            dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
            errorStream =new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String msg ="";
            String line;
            while ((line = errorStream.readLine()) !=null) {
                msg += line;
            }
            if (!msg.contains("Failure")) {
                result =true;
            }
        }catch (Exception e) {
            LogUtils.e("安装出错："+e.getMessage());
            return false;
        }finally {
            try {
                if (dataOutputStream !=null) {
                    dataOutputStream.close();
                }
                if (errorStream !=null) {
                    errorStream.close();
                }
            }catch (IOException e) {
                LogUtils.e("安装出错："+e.getMessage());
                return false;
            }
        }

        return result;
    }

    public static boolean isSystemApp( Context context) {
        return isSystemApp(context, context.getPackageName());
    }

    /**
     * 判断App是否是系统应用
     *
     * @param context     上下文
     * @param packageName 包名
     * @return `true`: 是<br></br>`false`: 否
     */
    public static boolean isSystemApp(Context context, String packageName) {
        if (RxDataTool.isNullString(packageName)){
            return false;
        }else {
            try {
                PackageManager pm = context.getPackageManager();
                ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
                if(ai.flags!=0 && ApplicationInfo.FLAG_SYSTEM != 0){
                    return true;
                }else {
                    return false;
                }

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * 判断App是否有root权限
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    public static boolean isAppRoot(){
        RxShellTool.CommandResult result = RxShellTool.execCmd("echo root", true);
        if (result.result == 0) {
            return true;
        }
        if (result.errorMsg != null) {
            LogUtils.e("isAppRoot:"+ result.errorMsg);
        }
        return false;
    }


}
