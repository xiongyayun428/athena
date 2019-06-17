package com.xyy.athena.core.utils;

import com.xyy.athena.core.exception.AthenaException;
import com.xyy.athena.core.exception.AthenaRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 安全的日期格式
 *
 * @author: 熊亚运
 * @date: 2019-06-17
 */
@Slf4j
public class SafeDate {
    private static final ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<>();
    public static final String DATE_ISO_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT = "000000";
    public static final String TIME_ISO_FORMAT = "HH:mm:ss";
    public static final String DATETIME_ISO_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_COMPACT_FORMAT = "yyyyMMdd";
    public static final String TIME_COMPACT_FORMAT = "HHmmss";
    public static final String DATETIME_COMPACT_FORMAT = "yyyyMMddHHmmss";

    public static SimpleDateFormat getDateFormat(String pattern) {
        if (sdf.get() == null) {
            sdf.set(new SimpleDateFormat());
        }
        SimpleDateFormat local = sdf.get();
        local.applyPattern(pattern);
        return local;
    }

    public String format(Object obj) {
        SimpleDateFormat sdf = getDateFormat(DATETIME_ISO_FORMAT);
        return sdf.format(obj);
    }
    public String format(String pattern, Object obj) {
        SimpleDateFormat sdf = getDateFormat(pattern);
        return sdf.format(obj);
    }

    public Object parse(String str) throws AthenaException {
        SimpleDateFormat dec = getDateFormat(DATETIME_ISO_FORMAT);
        try {
            Date d = dec.parse(str);
            return coerceDate(d, Date.class);
        } catch (ParseException pe) {
            throw new AthenaException("Date parse error, value: " + str + "offset: " + pe.getErrorOffset());
        }
    }
    public static String formatISODate(java.sql.Date date) {
        return getDateFormat(DATE_ISO_FORMAT).format(date);
    }

    public static String formatISOTime(java.sql.Time time) {
        return getDateFormat(TIME_ISO_FORMAT).format(time);
    }

    public static String formatISODatetime(java.sql.Timestamp timestamp) {
        return getDateFormat(DATETIME_ISO_FORMAT).format(timestamp);
    }

    public static String formatCompactDate(java.sql.Date date) {
        return getDateFormat(DATE_COMPACT_FORMAT).format(date);
    }

    public static String formatCompactDate(java.util.Date date) {
        return getDateFormat(DATE_COMPACT_FORMAT).format(date);
    }

    public static String formatCompactTime(java.sql.Time time) {
        return getDateFormat(TIME_COMPACT_FORMAT).format(time);
    }

    public static String formatCompactDatetime(java.sql.Timestamp timestamp) {
        return getDateFormat(DATETIME_COMPACT_FORMAT).format(timestamp);
    }

    public static String formatCompactDatetime(Date date) {
        return getDateFormat(DATETIME_COMPACT_FORMAT).format(date);
    }

    public static String formatDateWithPattern(Object obj, String pattern) {
        SimpleDateFormat sdf = getDateFormat(pattern);
        return sdf.format(obj);
    }

    public static java.sql.Date parseISODate(String dateStr) {
        return (java.sql.Date) parse(dateStr, getDateFormat(DATE_ISO_FORMAT), java.sql.Date.class);
    }

    public static java.sql.Time parseISOTime(String timeStr) {
        return (java.sql.Time) parse(timeStr, getDateFormat(TIME_ISO_FORMAT), java.sql.Time.class);
    }

    public static java.sql.Timestamp parseISODatetime(String dateStr) {
        return (java.sql.Timestamp) parse(dateStr, getDateFormat(DATETIME_ISO_FORMAT), java.sql.Timestamp.class);
    }

    public static java.sql.Date parseCompactDate(String dateStr) {
        return (java.sql.Date) parse(dateStr, getDateFormat(DATE_COMPACT_FORMAT), java.sql.Date.class);
    }

    public static java.sql.Time parseCompactTime(String timeStr) {
        return (java.sql.Time) parse(timeStr, getDateFormat(TIME_COMPACT_FORMAT), java.sql.Time.class);
    }

    public static java.sql.Timestamp parseCompactDatetime(String dateStr) {
        return (java.sql.Timestamp) parse(dateStr, getDateFormat(DATETIME_COMPACT_FORMAT), java.sql.Timestamp.class);
    }

    public static java.sql.Timestamp parseCompactDateFormat(String dateStr) {
        return (java.sql.Timestamp) parse(dateStr + DATE_FORMAT, getDateFormat(DATETIME_COMPACT_FORMAT),
                java.sql.Timestamp.class);
    }

    @SuppressWarnings("unchecked")
    private static Date parse(String dateStr, DateFormat format, Class clazz) {
        Date ret = null;

        try {
            ret = format.parse(dateStr);
            return newInstance(clazz, ret.getTime());
        } catch (ParseException e) {
            log.warn("date format error! format:" + ((SimpleDateFormat) format).toPattern() + ",input:" + dateStr, e);
            // slient skip exception, just return null;
            return null;
        }

    }

    private static Calendar getCalendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    public static int getYear(Date date) {
        return getCalendar(date).get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        return getCalendar(date).get(Calendar.MONTH) + 1;
    }

    public static int getDay(Date date) {
        return getCalendar(date).get(Calendar.DAY_OF_MONTH);
    }

    public static final Calendar date2Calendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    public static boolean isMonth(Date batchDate) {
        if (batchDate == null) {
            return false;
        }
        Calendar c = date2Calendar(batchDate);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return day == 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return day == 30;
        }
        if (month == 2) {
            return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0) ? day == 29 : day == 28;
        }
        return false;
    }

    public static boolean isQuarter(Date batchDate) {
        if (batchDate == null) {
            return false;
        }
        Calendar c = date2Calendar(batchDate);
        // int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);
        return (month == 3 && day == 31) || (month == 6 && day == 30) || (month == 9 && day == 30)
                || (month == 12 && day == 31);
    }

    public static boolean isHalfyear(Date batchDate) {
        if (batchDate == null) {
            return false;
        }
        Calendar c = date2Calendar(batchDate);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);
        return (month == 6 && day == 30) || (month == 12 && day == 31);
    }

    public static boolean isYear(Date batchDate) {
        if (batchDate == null) {
            return false;
        }
        Calendar c = date2Calendar(batchDate);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);
        return (month == 12 && day == 31);
    }
    @SuppressWarnings("unchecked")
    public static Date parse(String dateStr, String pattern, Class clazz) {
        Date ret = null;

        try {
            SimpleDateFormat format = getDateFormat(pattern);
            ret = format.parse(dateStr);
        } catch (Exception e) {
            throw new AthenaRuntimeException("date format error! format:" + pattern + ",input:" + dateStr, e);
        }

        if (clazz == null || clazz == Date.class)
            return ret;
        return newInstance(clazz, ret.getTime());
    }
    public Object parse(String str, Class<Date> clazz) throws AthenaException {
        SimpleDateFormat dec = getDateFormat(DATETIME_ISO_FORMAT);
        try {
            Date d = dec.parse(str);
            return coerceDate(d, clazz);
        } catch (ParseException pe) {
            throw new AthenaException("Date parse error, value: " + str + "offset: " + pe.getErrorOffset());
        }
    }

    private Date coerceDate(Date d, Class c) {
        if (c.isAssignableFrom(d.getClass())) {
            return d;
        }
        if (c == java.sql.Date.class) {
            return new java.sql.Date(d.getTime());
        }
        if (c == Time.class) {
            return new Time(d.getTime());
        }
        if (c == Timestamp.class) {
            return new Timestamp(d.getTime());
        }
        return null;
    }

    public static Date roll(Date now, int year, int month, int day, int hour, int minute, int second) {
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.YEAR, year);
        c.add(Calendar.MONTH, month);
        c.add(Calendar.DATE, day);
        c.add(Calendar.HOUR_OF_DAY, hour);
        c.add(Calendar.MINUTE, minute);
        c.add(Calendar.SECOND, second);
        if (Date.class == now.getClass()) {
            return c.getTime();
        }
        return newInstance(now.getClass(), c.getTimeInMillis());
    }

    public static Date rollTime(Date now, int hour, int minute, int second) {
        return roll(now, 0, 0, 0, hour, minute, second);
    }

    public static Date rollDate(Date now, int year, int month, int date) {
        return roll(now, year, month, date, 0, 0, 0);
    }

    private static Date newInstance(Class clazz, long newMillis) {
        try {
            Constructor c = clazz.getConstructor(new Class[] { long.class });
            Date d = (Date) c.newInstance(new Object[] { Long.valueOf(newMillis) });
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    public static java.util.Date rollDateByDateUnit(java.util.Date currentDate, int dateUnit, boolean prev, int dateUnitType) {
        Calendar calendar = GregorianCalendar.getInstance(Locale.getDefault());
        if (currentDate != null) {
            calendar.setTime(currentDate);
        }
        calendar.add(dateUnitType, prev ? -dateUnit : dateUnit);
        return new java.util.Date(calendar.getTime().getTime());
    }

    public static java.util.Date rollDateByDay(java.util.Date currentDate, int day, boolean prev) {
        Calendar calendar = GregorianCalendar.getInstance(Locale.getDefault());
        if (currentDate != null) {
            calendar.setTime(currentDate);
        }
        calendar.add(5, prev ? -day : day);
        return new java.util.Date(calendar.getTime().getTime());
    }

    public static java.util.Date rollDateByWeek(java.util.Date currentDate,
                                                int week, boolean prev) {
        Calendar calendar = GregorianCalendar.getInstance(Locale.getDefault());
        if (currentDate != null) {
            calendar.setTime(currentDate);
        }
        calendar.add(4, prev ? -week : week);
        return new java.util.Date(calendar.getTime().getTime());
    }

    public static java.util.Date rollDateByMonth(java.util.Date currentDate,
                                                 int month, boolean prev) {
        Calendar calendar = GregorianCalendar.getInstance(Locale.getDefault());
        if (currentDate != null) {
            calendar.setTime(currentDate);
        }
        // 2是对月份操作
        calendar.add(Calendar.MONTH, prev ? -month : month);
        return new java.util.Date(calendar.getTime().getTime());
    }

    public static java.util.Date rollDateByYear(java.util.Date currentDate, int year, boolean prev) {
        Calendar calendar = GregorianCalendar.getInstance(Locale.getDefault());
        if (currentDate != null) {
            calendar.setTime(currentDate);
        }
        calendar.add(Calendar.YEAR, prev ? -year : year);
        return new java.util.Date(calendar.getTime().getTime());
    }

    public static java.util.Date getFirstDayOfMonth(java.util.Date currentDate) {
        Calendar calendar = GregorianCalendar.getInstance(Locale.getDefault());
        if (currentDate != null) {
            calendar.setTime(currentDate);
        }
        calendar.set(5, 1);
        return new java.util.Date(calendar.getTime().getTime());
    }

    public String getChineseWeek(java.util.Date currentDate) {
        Calendar cal = GregorianCalendar.getInstance(Locale.getDefault());
        if (currentDate != null) {
            cal.setTime(currentDate);
        }
        String[] dayNames = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        int dayOfWeek = cal.get(7);
        System.out.println(dayNames[(dayOfWeek - 1)]);
        return dayNames[(dayOfWeek - 1)];
    }

    public Calendar getNextMonday(java.util.Date currentDate) {
        Calendar cal = GregorianCalendar.getInstance(Locale.getDefault());
        if (currentDate != null) {
            cal.setTime(currentDate);
        }
        Calendar result = null;
        result = cal;
        do {
            result = (Calendar) result.clone();
            result.add(5, 1);
        } while (result.get(7) != 2);
        return result;
    }

    public Calendar getNextSundayToFriday(java.util.Date currentDate, int date) {
        Calendar cal = GregorianCalendar.getInstance(Locale.getDefault());
        int d = -1;
        if (currentDate != null) {
            cal.setTime(currentDate);
        }
        if ((date >= 0) && (date <= 6)) {
            d = date;
        } else {
            log.error("获得当前日期计算出下一个星期的任何一天的日期,该方法指定的下个星期几输入错误!");
            return cal;
        }
        Calendar result = null;
        result = cal;
        do {
            result = (Calendar) result.clone();
            result.add(5, 1);
        } while (result.get(7) != d + 1);
        return result;
    }

    public static final String getCurrentDate() {
        Calendar cldNow = Calendar.getInstance();
        int iYear = cldNow.get(1);
        int iMonth = cldNow.get(2) + 1;
        int iDay = cldNow.get(5);
        return iYear
                + (iMonth < 10 ? "0" + iMonth : new StringBuilder().append(
                iMonth).toString())
                + (iDay < 10 ? "0" + iDay : new StringBuilder().append(iDay)
                .toString());
    }

    public static final String getCurrentDateTime() {
        Calendar cldNow = Calendar.getInstance();
        int iYear = cldNow.get(1);
        int iMonth = cldNow.get(2) + 1;
        int iDay = cldNow.get(5);
        int iHour = cldNow.get(11);
        int iMinute = cldNow.get(12);
        int iSecond = cldNow.get(13);

        return iYear
                + (iMonth < 10 ? "0" + iMonth : new StringBuilder().append(
                iMonth).toString())
                + (iDay < 10 ? "0" + iDay : new StringBuilder().append(iDay)
                .toString())
                + (iHour < 10 ? "0" + iHour : new StringBuilder().append(iHour)
                .toString())
                + (iMinute < 10 ? "0" + iMinute : new StringBuilder().append(
                iMinute).toString())
                + (iSecond < 10 ? "0" + iSecond : new StringBuilder().append(
                iSecond).toString());
    }

    public static final int getCurrentDay() {
        Calendar cldNow = Calendar.getInstance();
        int iDay = cldNow.get(5);
        return iDay;
    }

    public static final int getCurrentMonth() {
        Calendar cldNow = Calendar.getInstance();
        int iMonth = cldNow.get(2) + 1;
        return iMonth;
    }

    public static final String getCurrentTime() {
        Calendar cldNow = Calendar.getInstance();
        int iHour = cldNow.get(11);
        int iMinute = cldNow.get(12);
        int iSecond = cldNow.get(13);

        return (iHour < 10 ? "0" + iHour : new StringBuilder().append(iHour)
                .toString())
                + (iMinute < 10 ? "0" + iMinute : new StringBuilder().append(
                iMinute).toString())
                + (iSecond < 10 ? "0" + iSecond : new StringBuilder().append(
                iSecond).toString());
    }

    public static final int getCurrentYear() {
        Calendar cldNow = Calendar.getInstance();
        return cldNow.get(1);
    }

    public static final String getPrevMonth1stDay(String sDate) {
        int iMonth = Integer.parseInt(sDate.substring(4, 6)) - 1;
        if (iMonth > 0) {
            return sDate.substring(0, 4)
                    + (iMonth < 10 ? "0" + iMonth : new StringBuilder().append(
                    iMonth).toString()) + "01";
        }
        int iYear = Integer.parseInt(sDate.substring(0, 4)) - 1;
        return iYear + "1201";
    }

    public static Timestamp getStartTime(java.util.Date startTime) {
        if (startTime == null) {
            return null;
        }
        return new Timestamp(startTime.getYear(), startTime.getMonth(),
                startTime.getDate(), 0, 0, 0, 1);
    }

    public static Timestamp getEndTime(java.util.Date endTime) {
        if (endTime == null) {
            return null;
        }
        return new Timestamp(endTime.getYear(), endTime.getMonth(),
                endTime.getDate(), 23, 59, 59, 999999999);
    }

    /**
     * 计算时间
     * @param started		开始时间
     * @param finished		结束时间
     * @return
     */
    public static String calculateTime(long started, long finished) {
        long ms = finished - started;
        if (ms < 1000) {
            return ms + " ms.";
        } else if (ms >= 1000 && ms < 60 * 1000) {
            // 一分钟内
            return (ms / 1000 + "." + ms % 1000) + " s.";
        } else if (ms >= 60 * 1000 && ms < 60 * 60 * 1000) {
            // 一小时内
            // 分钟
            long minute = ms / (60 * 1000);
            // 秒钟
            long second = (ms - minute * 60 * 1000) / 1000 % 1000;
            return (minute + "." + second) + " m.";
        } else if (ms >= 3600000) {
            // 一小时以上
            long hour = ms / (60 * 60 * 1000);
            long minute = (ms - hour * 60 * 60 * 1000) / (60 * 1000);
            long second = (ms - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;
            return (hour + ":" + minute + "." + second) + " h.";
        } else {
            return ms + " ms.";
        }
    }
}
