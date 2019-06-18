package com.xiongyayun.athena.core.utils;

import java.text.DecimalFormat;

/**
 * FileUtil
 *
 * @author: 熊亚运
 * @date: 2019-06-17
 */
public class FileUtil {
    public static String getFileSize(Long fileSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileSize.longValue() < 1024L) {
            fileSizeString = df.format(fileSize.longValue()) + "B";
        } else if (fileSize.longValue() < 1048576L) {
            fileSizeString = df.format(fileSize.longValue() / 1024.0D) + "K";
        } else if (fileSize.longValue() < 1073741824L) {
            fileSizeString = df.format(fileSize.longValue() / 1048576.0D) + "M";
        } else {
            fileSizeString = df.format(fileSize.longValue() / 1073741824.0D) + "G";
        }
        return fileSizeString;
    }
}
