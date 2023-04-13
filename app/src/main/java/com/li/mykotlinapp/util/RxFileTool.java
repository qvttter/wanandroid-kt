package com.li.mykotlinapp.util;

import java.io.File;

/************************************************************************
 *@Project: android
 *@Package_Name: com.easyway.pdp.utils
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/3/17
 *@Copyright:(C)2022 . All rights reserved.
 *************************************************************************/
public class RxFileTool {
    public static File getFileByPath(String filePath) {
        if (isNullString(filePath)) {
            return null;
        } else {
            return new File(filePath);
        }
    }

    public static boolean isNullString(String str) {
        return str == null || str.length() == 0 || str.equals("null");
    }

    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }
}
