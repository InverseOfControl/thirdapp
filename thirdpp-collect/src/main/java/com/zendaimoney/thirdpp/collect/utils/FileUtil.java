package com.zendaimoney.thirdpp.collect.utils;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;

/**
 * Created by YM20051 on 2017/7/5.
 */
public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
    /**
     * 获取指定路径下的第一个文件
     *
     * @param filePath
     * @return
     */
    public static File getFirstFile(String filePath) {

        File file = new File(filePath);
        if(file.exists()){
            File[] files = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return pathname.getName().endsWith("xls") || pathname.getName().endsWith("xlsx");
                }
            });

            if(files != null && files.length > 0) {
                return files[0];
            }
        }

        return null;
    }

    /**
     * 转移文件到指定的目录下
     *
     * @param filePath
     */
    public static String move(File file, String filePath) {
        String currdate = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
        File destFile = new File(filePath+File.separator+currdate);
        if(!destFile.exists()){
            destFile.mkdirs();
        }

        String newFileName = copy(file, destFile.getAbsolutePath());

        file.delete();

        return newFileName;
    }

    public static String copy(File file, String newPath) {
        String newFileName = newPath+File.separator+file.getName();
        try {
            int byteRead = 0;
            if (file.exists()) {  //文件存在时
                InputStream inStream = new FileInputStream(file);  //读入原文件


                FileOutputStream fs = new FileOutputStream(newFileName);
                byte[] buffer = new byte[1444];
                while ((byteRead = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteRead);
                }
                fs.flush();
                fs.close();
                inStream.close();
            }
        } catch (Exception e) {
            logger.error("复制文件失败:", e);
            return null;
        }
        return newFileName;
    }

    // 重命名文件
    public static void rename(String fileName, String append){
        File file = new File(fileName);
        if(file.exists()){
            String preName = fileName.substring(0, fileName.lastIndexOf("."));
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String newName = preName+"_"+append+ suffixName;

            file.renameTo(new File(newName));
        }

    }
}
