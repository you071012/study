package com.ukar.study.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 使用ThreadLocal解决SimpleDateFormat线程不安全问题
 *
 * jdk8以上可以使用LocalDate，LocaleTime，LocaleDateTime等新类来解决这个问题
 */
public class ThreadLocalDateUtil {
    private static final ThreadLocal<SimpleDateFormat> DATE_TIME_LOCAL =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    private static final ThreadLocal<SimpleDateFormat> DATE_LOCAL =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    private static final ThreadLocal<SimpleDateFormat> TIME_LOCAL =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("HH:mm:ss"));

    public static String foramtDate(Date date){
        return format(date, DATE_LOCAL);
    }

    public static String foramtDateTime(Date date){
        return format(date, DATE_TIME_LOCAL);
    }

    public static String foramtTime(Date date){
        return format(date, TIME_LOCAL);
    }

    public static Date parseDateTime(String str){
        try {
            //设置非宽松解析模式，默认宽松解析，例如将20210133解析成功
            DATE_TIME_LOCAL.get().setLenient(false);
            return DATE_TIME_LOCAL.get().parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }finally {
            DATE_TIME_LOCAL.remove();
        }
    }

    private static String format(Date date, ThreadLocal<SimpleDateFormat> threadLocal){
        try{
            return threadLocal.get().format(date);
        }finally {
            threadLocal.remove();
        }

    }

    public static void main(String[] args) {
        System.out.println(ThreadLocalDateUtil.foramtDate(new Date()));
        System.out.println(ThreadLocalDateUtil.foramtDateTime(new Date()));
        System.out.println(ThreadLocalDateUtil.foramtTime(new Date()));
        System.out.println(ThreadLocalDateUtil.parseDateTime("2021-01-33 00:00:00"));
    }
}
