package com.ukar.study.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DateFormateTestUtil {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ExecutorService executorService = Executors.newCachedThreadPool();


    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL =
            ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    /**
     * 打印出false或出现异常，证明线程不安全
     */
    public void test() {
        while (true) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    String dateString = simpleDateFormat.format(new Date());
                    try {
                        Date parseDate = simpleDateFormat.parse(dateString);
                        String dateString2 = simpleDateFormat.format(parseDate);

                        boolean equals = dateString.equals(dateString2);
                        System.out.println(equals);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void test2() {
        while (true) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    String dateString = ThreadLocalDateUtil.foramtDateTime(new Date());
                    try {
                        Date parseDate = ThreadLocalDateUtil.parseDateTime(dateString);
                        String dateString2 = ThreadLocalDateUtil.foramtDateTime(parseDate);

                        boolean equals = dateString.equals(dateString2);
                        System.out.println(equals);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        DateFormateTestUtil dateFormateTest = new DateFormateTestUtil();
        dateFormateTest.test2();

    }
}
