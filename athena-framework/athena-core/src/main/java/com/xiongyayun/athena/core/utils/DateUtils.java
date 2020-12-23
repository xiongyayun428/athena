//package com.xiongyayun.athena.core.utils;
//
//import lombok.extern.slf4j.Slf4j;
//
//import java.lang.reflect.Constructor;
//import java.sql.Timestamp;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * DateUtils
// *
// * @author 熊亚运
// * @date 2019-06-17
// */
//@Slf4j
//public class DateUtils {
//    public static final int MILLIS_IN_SECOND = 1000;
//    public static final int MILLIS_IN_MINUTE = 60 * 1000;
//    public static final int MILLIS_IN_HOUR = 60 * 60 * 1000;
//    public static final int MILLIS_IN_DAY = 24 * 60 * 60 * 1000;
//
//    public static java.sql.Date toSqlDate(String str, String pattern) {
//        if (pattern == null)
//            pattern = DATE_COMPACT_FORMAT;
//
//        com.hljinke.common.utils.DateFormat sdf = new com.hljinke.common.utils.DateFormat(pattern, java.sql.Date.class);
//
//        try {
//            return (java.sql.Date) sdf.parse(str);
//        } catch (Exception e) {
//            // log.warn(e);
//            // e.printStackTrace();
//            // log.error("input:"+str+" pattern:"+pattern);
//            return null;
//        }
//    }
//
//    public static Date toDate(String str, String pattern) {
//        SimpleDateFormat sdf = null;
//        if (pattern.equals(DATE_COMPACT_FORMAT))
//            sdf = (SimpleDateFormat) getDateFormat(DATE_COMPACT_FORMAT);
//        else if (pattern.equals(DATETIME_COMPACT_FORMAT)) {
//            sdf = (SimpleDateFormat) getDateFormat(DATETIME_COMPACT_FORMAT);
//        }
//
//        else {
//            sdf = getDateFormat(pattern);
//        }
//        try {
//            return sdf.parse(str);
//        } catch (Exception e) {
//            // log.warn(e);
//            // e.printStackTrace();
//            // log.error("input:"+str+" pattern:"+pattern);
//            return null;
//        }
//    }
//
//    public DateUtils() {
//    }
//
//    public static java.sql.Date getCurrentDate() {
//        return new java.sql.Date(System.currentTimeMillis());
//    }
//
//    public static int diffYear(Date d1, Date d2) {
//        Calendar c1 = Calendar.getInstance();
//        c1.setTime(d1);
//        Calendar c2 = Calendar.getInstance();
//        c2.setTime(d2);
//        return c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
//    }
//
//    public static int diffMonth(Date d1, Date d2) {
//        Calendar c1 = Calendar.getInstance();
//        c1.setTime(d1);
//        Calendar c2 = Calendar.getInstance();
//        c2.setTime(d2);
//        int m = c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
//        int y = c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
//        return y * 12 + m;
//    }
//
//    public static int diffWeek(Date d1, Date d2) {
//        int d = diffDay(d1, d2);
//        if (d == 0)
//            return 0;
//        Calendar c2 = Calendar.getInstance();
//        c2.setTime(d2);
//        int w2 = c2.get(Calendar.DAY_OF_WEEK);
//        if (c2.getFirstDayOfWeek() != Calendar.SUNDAY)
//            w2 = (w2 == Calendar.SUNDAY) ? 7 : w2 - 1;
//        int w = d / 7;
//        int dw = (w2 + d % 7);
//        if (dw < 1)
//            return w - 1;
//        if (dw > 7)
//            return w + 1;
//        return w;
//    }
//
//    public static int diffDay(Date d1, Date d2) {
//        long offset = TimeZone.getDefault().getRawOffset() + TimeZone.getDefault().getDSTSavings();
//        return (int) ((d1.getTime() + offset) / MILLIS_IN_DAY - (d2.getTime() + offset) / MILLIS_IN_DAY);
//    }
//
//    public static int diffHour(Date d1, Date d2) {
//        return (int) (d1.getTime() / MILLIS_IN_HOUR - d2.getTime() / MILLIS_IN_HOUR);
//    }
//
//    public static int diffMinute(Date d1, Date d2) {
//        return (int) (d1.getTime() / MILLIS_IN_MINUTE - d2.getTime() / MILLIS_IN_MINUTE);
//    }
//
//    public static int diffSecond(Date d1, Date d2) {
//        return (int) (d1.getTime() / MILLIS_IN_SECOND - d2.getTime() / MILLIS_IN_SECOND);
//    }
//
//    public static Date roll(Date now, int year, int month, int day, int hour, int minute, int second) {
//        Calendar c = Calendar.getInstance();
//        c.setTime(now);
//        c.add(Calendar.YEAR, year);
//        c.add(Calendar.MONTH, month);
//        c.add(Calendar.DATE, day);
//        c.add(Calendar.HOUR_OF_DAY, hour);
//        c.add(Calendar.MINUTE, minute);
//        c.add(Calendar.SECOND, second);
//        if (Date.class == now.getClass())
//            return c.getTime();
//        return newInstance(now.getClass(), c.getTimeInMillis());
//    }
//
//    public static Date rollTime(Date now, int hour, int minute, int second) {
//        return roll(now, 0, 0, 0, hour, minute, second);
//    }
//
//    public static Date rollDate(Date now, int year, int month, int date) {
//        return roll(now, year, month, date, 0, 0, 0);
//    }
//
//    public static Date rollDate(Date now, int day) {
//        Calendar c = Calendar.getInstance();
//        c.setTime(now);
//        c.add(Calendar.DATE, day);
//        if (Date.class == now.getClass())
//            return c.getTime();
//        return newInstance(now.getClass(), c.getTimeInMillis());
//    }
//
//    public static Date rollWeek(Date now, int week, int day) {
//        Calendar c = Calendar.getInstance();
//        c.setTime(now);
//        c.add(Calendar.WEEK_OF_YEAR, week);
//        c.add(Calendar.DAY_OF_WEEK, day);
//        if (Date.class == now.getClass())
//            return c.getTime();
//        return newInstance(now.getClass(), c.getTimeInMillis());
//    }
//
//    @SuppressWarnings("unchecked")
//    private static Date newInstance(Class clazz, long newMillis) {
//        try {
//            Constructor c = clazz.getConstructor(new Class[] { long.class });
//            Date d = (Date) c.newInstance(new Object[] { new Long(newMillis) });
//            return d;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    // ---------- general format and parse method -------------
//
//    public static String format(Date date, String pattern) {
//        String ret = null;
//        SimpleDateFormat format = getDateFormat(pattern);
//        ret = format.format(date);
//        return ret;
//    }
//
//    public static String formatDate(Date date, String pattern) {
//        if (date == null)
//            return StringUtils.formatString("", pattern.length());
//        SimpleDateFormat sdf = getDateFormat(pattern);
//        return sdf.format(date);
//    }
//
//    public static String formatDatetime(Date date, String pattern) {
//        if (date == null)
//            return StringUtils.formatString("", pattern.length());
//        SimpleDateFormat sdf = getDateFormat(pattern);
//        return sdf.format(date);
//    }
//
//    /**
//     * 以指定的格式返回Date数据
//     *
//     * @param dateStr
//     * @param pattern
//     * @return
//     */
//    public static Date parse(String dateStr, String pattern) {
//        return parse(dateStr, pattern, null);
//    }
//
//
//
//    // --------- special format and parse method --------------
//
//    public static final String DATE_ISO_FORMAT = "yyyy-MM-dd";
//    public static final String DATE_FORMAT = "000000";
//    public static final String TIME_ISO_FORMAT = "HH:mm:ss";
//    public static final String DATETIME_ISO_FORMAT = "yyyy-MM-dd HH:mm:ss";
//    public static final String ISO8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
//    public static final String DATE_COMPACT_FORMAT = "yyyyMMdd";
//    public static final String TIME_COMPACT_FORMAT = "HHmmss";
//    public static final String DATETIME_COMPACT_FORMAT = "yyyyMMddHHmmss";
//    public static final String TIME_ISO8601_COMPACT_FORMAT = "HHmmssSSS";
//
//
//
//    public static void main(String[] args) {
//        System.out.println(format(new Date(), DATE_COMPACT_FORMAT));
//        System.out.println(format(new Date(), "yyyy年MM月dd日"));
//        /*
//         * Date date = new Date(System.currentTimeMillis()); Timestamp tt = new
//         * Timestamp(System.currentTimeMillis());
//         * System.err.println("hhmmss:"+DateUtils.formatDateWithPattern(tt,
//         * "hhmmss"));
//         * System.err.println("yyyymmdd:"+DateUtils.formatDateWithPattern(date,
//         * "yymmdd"));
//         */
//        System.err.println(DateUtils.rollDate(DateUtils.rollDate(new Date(), 0, -3, 0), 0, -3, 0));
//        // TimeZone z = TimeZone.getDefault();
//        // Date d1 = new Date();
//        // java.sql.Date d2 =
//        // new java.sql.Date(
//        // System.currentTimeMillis() + 60000 * 60 * 43 + 34000);
//        // d2 = (java.sql.Date)rollDate(d2, 3, 4, 1);
//        // java.sql.Time t = new java.sql.Time(System.currentTimeMillis());
//        // java.sql.Timestamp t1 =
//        // new java.sql.Timestamp(System.currentTimeMillis());
//        // System.err.println("compare:" + diffDay(d1, d2));
//        // System.err.println("compare:" + diffHour(d1, d2));
//        // System.err.println("compare:" + diffMinute(d1, d2));
//        // System.err.println("compare:" + diffSecond(d1, d2));
//        // System.err.println("compare:" + diffWeek(d1, d2));
//        // System.err.println("compare:" + diffMonth(d1, d2));
//        // System.err.println("compare:" + diffYear(d1, d2));
//        //
//        // System.err.println("format:" + format(d2, "yyyy-MM-dd HH:mm:ss"));
//        // System.err.println("format:" + formatCompactDate(d2));
//        // System.err.println("format:" + formatCompactDatetime(t1));
//        // System.err.println("format:" + formatCompactTime(t));
//        // System.err.println("format:" + formatISODate(d2));
//        // System.err.println("format:" + formatISODatetime(t1));
//        // System.err.println("format:" + formatISOTime(t));
//        //
//        // System.err.println(parse("2002-01-01 13:22:11", "yyyy-MM-dd
//        // HH:mm:ss"));
//        // System.err.println(
//        // parse("2002-01-01 13:22:11", "yyyy-MM-dd HH:mm:ss", Date.class));
//        // System.err.println(parseISODate("2001-01-01"));
//        // System.err.println(parseISOTime("23:46:11"));
//        // System.err.println(parseISODatetime("2001-01-01 23:46:11"));
//        //
//        // System.err.println(parseCompactDate("20010101"));
//        // System.err.println(parseCompactTime("234611"));
//        // System.err.println(parseCompactDatetime("20000101234611"));
//        //
//        // System.err.println(rollDate(t1, 3, 0, 1));
//        // System.err.println(rollTime(t1, -3, 4, 30));
//        // System.err.println(rollWeek(t1, -3, 4));
//        //
//        // System.err.println(formatCompactDatetime(new Date()));
//
//    }
//
//    public static final Timestamp toTimestamp(String str, String pattern) {
//        SimpleDateFormat sdf = getDateFormat(pattern);
//        try {
//            return new Timestamp(sdf.parse(str).getTime());
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    /**
//     * String to Date, time format string is :
//     *
//     * @param time
//     * @return
//     */
//    final public static java.sql.Date getTime(String time) {
//        if (time == null) {
//            return null;
//        }
//        int pos = time.indexOf(":");
//        String strhour = time;
//        String strminute = "0";
//        if (pos != -1) {
//            strhour = time.substring(0, pos);
//            strminute = time.substring(pos + 1);
//        }
//        int hour = 0;
//        int minute = 0;
//        try {
//            hour = Integer.parseInt(strhour);
//            minute = Integer.parseInt(strminute);
//        } catch (NumberFormatException e) {
//            return null;
//        }
//        Calendar cd = Calendar.getInstance();
//        cd.set(Calendar.HOUR_OF_DAY, hour);
//        cd.set(Calendar.MINUTE, minute);
//        return new java.sql.Date(cd.getTime().getTime());
//    }
//
//    public static String formatyyyymmddDatetime(java.sql.Timestamp timestamp) {
//        return getDateFormat(DATE_COMPACT_FORMAT).format(timestamp);
//    }
//
//    private static final Map<String, ThreadLocal<SimpleDateFormat>> pool = new HashMap<String, ThreadLocal<SimpleDateFormat>>();
//    private static final Object lock = new Object();
//
//    private static ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>();
//
//    public static SimpleDateFormat getDateFormat(String pattern) {
//        ThreadLocal<SimpleDateFormat> tl = pool.get(pattern);
//        if (tl == null) {
//            synchronized (lock) {
//                tl = pool.get(pattern);
//                if (tl == null) {
//                    final String p = pattern;
//                    tl = new ThreadLocal<SimpleDateFormat>() {
//                        protected synchronized SimpleDateFormat initialValue() {
//                            return new SimpleDateFormat(p);
//                        }
//                    };
//                    pool.put(p, tl);
//                }
//            }
//        }
//        return tl.get();
//        /*
//         * if(sdf.get()==null){ sdf.set(new SimpleDateFormat()); }
//         * SimpleDateFormat local=sdf.get(); local.applyPattern(pattern); return
//         * local;
//         */
//    }
//
//    public static java.sql.Timestamp getCurrentTimestamp() {
//        return new java.sql.Timestamp(System.currentTimeMillis());
//    }
//
//    /**
//     * 日期相加减
//     * @param time
//     *             时间字符串 yyyy-MM-dd HH:mm:ss
//     * @param num
//     *             加的数，-num就是减去
//     * @return
//     *             减去相应的数量的年的日期
//     * @throws ParseException
//     */
//    public static Date yearAddNum(Date time, Integer num) {
//        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        //Date date = format.parse(time);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(time);
//        calendar.add(Calendar.YEAR, num);
//        Date newTime = calendar.getTime();
//        return newTime;
//    }
//
//    /**
//     *
//     * @param time
//     *           时间
//     * @param num
//     *           加的数，-num就是减去
//     * @return
//     *          减去相应的数量的月份的日期
//     * @throws ParseException Date
//     */
//    public static Date monthAddNum(Date time, Integer num){
//        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        //Date date = format.parse(time);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(time);
//        calendar.add(Calendar.MONTH, num);
//        Date newTime = calendar.getTime();
//        return newTime;
//    }
//
//    /**
//     *
//     * @param time
//     *           时间
//     * @param num
//     *           加的数，-num就是减去
//     * @return
//     *          减去相应的数量的天的日期
//     * @throws ParseException Date
//     */
//    public static Date dayAddNum(Date time, Integer num){
//        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        //Date date = format.parse(time);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(time);
//        calendar.add(Calendar.DAY_OF_MONTH, num);
//        Date newTime = calendar.getTime();
//        return newTime;
//    }
//}
