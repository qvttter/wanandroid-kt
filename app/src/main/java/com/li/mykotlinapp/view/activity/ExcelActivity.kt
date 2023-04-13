package com.li.mykotlinapp.view.activity

import android.R.attr.path
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.li.mykotlinapp.R
import com.li.mykotlinapp.adapter.ExcelFileAdapter
import com.li.mykotlinapp.adapter.ExcelItemListAdapter
import com.li.mykotlinapp.base.BaseActivity
import com.li.mykotlinapp.bean.ExcelFileBean
import com.li.mykotlinapp.bean.ExcelItemBean
import com.li.mykotlinapp.util.ExcelUtil
import com.li.mykotlinapp.widget.ExcelDialog
import kotlinx.android.synthetic.main.activity_excel.*
import java.io.File


/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.activity
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/10/20
 *@Copyright:(C)2020 . All rights reserved.
 *************************************************************************/
class ExcelActivity : BaseActivity() {
    private lateinit var previewAdapter: ExcelItemListAdapter
    private lateinit var previewList: MutableList<ExcelItemBean>

    private lateinit var fileAdapter: ExcelFileAdapter
    private lateinit var fileList: MutableList<ExcelFileBean>


    private var filePath = "/sdcard/AndroidExcelDemo"

    override fun getLayout(): Int {
        return R.layout.activity_excel
    }

    override fun initData() {
        previewList = ArrayList()
        previewAdapter = ExcelItemListAdapter(previewList)
        rcv_preview.adapter = previewAdapter
        rcv_preview.layoutManager = LinearLayoutManager(mContext)

        fileList = ArrayList()
        fileAdapter = ExcelFileAdapter(fileList)
        rcv_excel.adapter = fileAdapter
        rcv_excel.layoutManager = LinearLayoutManager(mContext)

        fileAdapter.setOnItemClickListener { adapter, view, position ->
            var bean = adapter.getItem(position) as ExcelFileBean
            openFile(File(bean.path))
        }

        fileAdapter.setOnItemLongClickListener { adapter, view, position ->
            var bean = adapter.getItem(position) as ExcelFileBean
            shareByQQ(File(bean.path))
            true
        }

        btn_add.setOnClickListener {
            var dialog = ExcelDialog.newInstance()
            dialog.setListener(object : ExcelDialog.OnCompleteListener {
                override fun onComplete(bean: ExcelItemBean) {
                    previewList.add(bean)
                    previewAdapter.notifyDataSetChanged()
                }
            })
            dialog.show(supportFragmentManager, "ExcelDialog")
        }

        btn_export.setOnClickListener {
            exportExcel()
        }

        btn_refresh.setOnClickListener {
            getAllExcelFile()
        }
    }

    //导出excel文件
    private fun exportExcel() {
        val file = File(filePath)
        if (!file.exists()) {
            file.mkdirs()
        }
        val excelFileName = "/e" + System.currentTimeMillis() + ".xls"
        val title = arrayOf("姓名", "年龄", "性别")
        val sheetName = "demoSheetName"
        filePath = filePath + excelFileName
        ExcelUtil.initExcel(filePath, sheetName, title)
        ExcelUtil.writeObjListToExcel(previewList, filePath, mContext)
        tv_tip.text = "excel已导出至：$filePath"
    }

    //遍历某文件夹下的所有excel文件
    fun getAllExcelFile() {
        fileList.clear()
        val file = File(filePath)
        if (file.listFiles() == null) {
            tv_tip.text = "file.listFiles()==null"
            return
        }
        val files: Array<File> = file.listFiles()
        for (i in files.indices) {
            if (!files[i].isDirectory) {
                val fileName = files[i].name
                if (fileName.trim { it <= ' ' }.toLowerCase().endsWith(".xls")) {
                    fileList.add(ExcelFileBean(fileName, files[i].absolutePath))
                }
            }
        }
        fileAdapter.notifyDataSetChanged()
    }

    val File.uri: Uri
        get() = if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(mContext, mContext.applicationContext.packageName + ".FileProvider", this)
        } else {
            Uri.fromFile(this)
        }

    fun openFile(file: File) {
        if (file.exists()) {
            try {
                startActivity(Intent(Intent.ACTION_VIEW)
                        .addFlags(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
                        } else {
                            Intent.FLAG_ACTIVITY_NEW_TASK
                        })
                        .setDataAndType(file.uri, getMIMEType(file)))
            } catch (e: Exception) {
                shortToast("无法打开此文件")
                e.printStackTrace()
            }
        }
    }

    fun shareByQQ(file: File) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        sendIntent.putExtra(Intent.EXTRA_STREAM, file.uri)
        sendIntent.type = "*/*"
        sendIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity") //QQ好友或QQ群

//sendIntent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");//微信朋友
//sendIntent.setClassName("com.tencent.mobileqq", "cooperation.qqfav.widget.QfavJumpActivity");//保存到QQ收藏
//sendIntent.setClassName("com.tencent.mobileqq", "cooperation.qlink.QlinkShareJumpActivity");//QQ面对面快传
//sendIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.qfileJumpActivity");//传给我的电脑
//sendIntent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");//微信朋友圈，仅支持分享图片
//sendIntent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");//微信朋友
//sendIntent.setClassName("com.tencent.mobileqq", "cooperation.qqfav.widget.QfavJumpActivity");//保存到QQ收藏
//sendIntent.setClassName("com.tencent.mobileqq", "cooperation.qlink.QlinkShareJumpActivity");//QQ面对面快传
//sendIntent.setClassName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.qfileJumpActivity");//传给我的电脑
//sendIntent.setClassName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");//微信朋友圈，仅支持分享图片
        startActivity(sendIntent)
    }

    fun zipFile() {

    }

    private val MIME_TABLE = mapOf(
            ".doc" to "application/msword",
            ".docx" to "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            ".xls" to "application/vnd.ms-excel",
            ".xlsx" to "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            ".pdf" to "application/pdf",
            ".pps" to "application/vnd.ms-powerpoint",
            ".ppt" to "application/vnd.ms-powerpoint",
            ".pptx" to "application/vnd.openxmlformats-officedocument.presentationml.presentation",
            ".z" to "application/x-compress",
            ".zip" to "application/x-zip-compressed")

    /**
     * 获取文件类型
     */
    fun getMIMEType(file: File): String {
        var type = "*/*"
        val fName = file.name

        val dotIndex = fName.lastIndexOf(".")
        if (dotIndex < 0) {
            return type
        }

        val end = fName.substring(dotIndex, fName.length).toLowerCase()
        if (end.isBlank()) return type

        type = MIME_TABLE[end] ?: return type
        return type
    }

    companion object {
        fun start(content: Context) {
            val intent = Intent(content, ExcelActivity().javaClass)
            content.startActivity(intent)
        }
    }
}