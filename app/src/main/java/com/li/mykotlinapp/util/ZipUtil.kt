package com.li.mykotlinapp.util

import java.io.*
import java.nio.charset.Charset
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.util
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/10/21
 *@Copyright:(C)2020 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class ZipUtil {

    private val BUFF_SIZE = 1024 * 1024 // 1M Byte

    /**
     * 批量压缩文件（夹）
     *
     * @param resFileList 要压缩的文件（夹）列表
     * @param zipFile 生成的压缩文件
     * @throws IOException 当压缩过程出错时抛出
     */
    @Throws(IOException::class)
    fun zipFiles(resFileList: Collection<File?>, zipFile: File?) {
        val zipout = ZipOutputStream(BufferedOutputStream(FileOutputStream(
                zipFile), BUFF_SIZE))
        for (resFile in resFileList) {
            zipFile(resFile!!, zipout, "")
        }
        zipout.close()
    }

    /**
     * 批量压缩文件（夹）
     *
     * @param resFileList 要压缩的文件（夹）列表
     * @param zipFile 生成的压缩文件
     * @param comment 压缩文件的注释
     * @throws IOException 当压缩过程出错时抛出
     */
    @Throws(IOException::class)
    fun zipFiles(resFileList: Collection<File?>, zipFile: File?, comment: String?) {
        val zipout = ZipOutputStream(BufferedOutputStream(FileOutputStream(
                zipFile), BUFF_SIZE))
        for (resFile in resFileList) {
            zipFile(resFile!!, zipout, "")
        }
        zipout.setComment(comment)
        zipout.close()
    }

    /**
     * 压缩文件
     *
     * @param resFile 需要压缩的文件（夹）
     * @param zipout 压缩的目的文件
     * @param rootpath 压缩的文件路径
     * @throws FileNotFoundException 找不到文件时抛出
     * @throws IOException 当压缩过程出错时抛出
     */
    @Throws(FileNotFoundException::class, IOException::class)
    private fun zipFile(resFile: File, zipout: ZipOutputStream, rootpath: String) {
        var rootpath = rootpath
        rootpath = (rootpath + (if (rootpath.trim { it <= ' ' }.length == 0) "" else File.separator)
                + resFile.getName())
        rootpath = String(rootpath.toByteArray(charset("8859_1")), Charset.forName("GB2312"))
        if (resFile.isDirectory()) {
            val fileList: Array<File> = resFile.listFiles()
            for (file in fileList) {
                zipFile(file, zipout, rootpath)
            }
        } else {
            val buffer = ByteArray(BUFF_SIZE)
            val `in` = BufferedInputStream(FileInputStream(resFile),
                    BUFF_SIZE)
            zipout.putNextEntry(ZipEntry(rootpath))
            var realLength: Int = 0
            while (`in`.read(buffer).also({ realLength = it }) != -1) {
                zipout.write(buffer, 0, realLength)
            }
            `in`.close()
            zipout.flush()
            zipout.closeEntry()
        }
    }
}