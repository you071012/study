package com.ukar.study.utils.safe;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jia.you
 * @Date: 2021/03/31/14:01
 * @Description: 介绍几种对象安全的发布方式
 * 假设现在需要发布一个安全的SafeUtil对象，有以下几种方式
 */
public class SafeDemo {

    //方式一，直接静态变量初始化
    public static SafeUtil safeUtil = new SafeUtil();

    //方式二，加同步锁方式
    private static SafeUtil safeUtil2;

    public static synchronized SafeUtil getSafeUtil(){
        if(safeUtil2 == null){
            safeUtil2 = new SafeUtil();
        }
        return safeUtil2;
    }

    //方式三，延时发布方式，只有在调用getSafeUtil2方法时才去发布SafeUtil对象
    private static class SafeHolder{
        private static SafeUtil safeUtil = new SafeUtil();
    }

    public static SafeUtil getSafeUtil2(){
        return SafeHolder.safeUtil;
    }


}
