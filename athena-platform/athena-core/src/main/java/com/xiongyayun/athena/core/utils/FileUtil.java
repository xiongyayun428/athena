package com.xiongyayun.athena.core.utils;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * FileUtil
 *
 * @author: 熊亚运
 * @date: 2019-06-17
 */
public class FileUtil {
	/**
	 * Field number for <code>get</code> and <code>set</code> indicating the
	 * year. This is a calendar-specific value; see subclass documentation.
	 */
	public static final int ERA = 0;

	/**
	 * Field number for <code>get</code> and <code>set</code> indicating the
	 * year. This is a calendar-specific value; see subclass documentation.
	 */
	public static final int YEAR = 1;

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

	/**
	 * Sets the given calendar field to the given value. The value is not
	 * interpreted by this method regardless of the leniency mode.
	 *
	 * @param field the given calendar field.
	 * @throws ArrayIndexOutOfBoundsException if the specified field is out of range
	 *             (<code>field &lt; 0 || field &gt;= FIELD_COUNT</code>).
	 * in non-lenient mode.
	 */
	public void get(int field)
	{
		Calendar c = Calendar.getInstance();
		System.out.println(c.get(field));
	}
}
