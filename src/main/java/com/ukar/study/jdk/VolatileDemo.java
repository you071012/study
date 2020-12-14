package com.ukar.study.jdk;

/**
 * 每个线程操作时都会有一个高速缓存区内存，当当前操作完成后，将高速缓存区内容刷新主内存中，
 * （正常情况是每个线程先从主内存中将变量拉到本地线程副本，后面该线程使用都是使用的当前副本）
 *  volatile关键字是通过缓存一致性，保证每个线程读取的变量值都是和当前主内存一致
 */
public class VolatileDemo {

    volatile boolean flag = false;
    int a = 0;

    public void addA(){
        while (!flag){
            a++;
        }
    }

    public static void main(String[] args) throws Exception{
        VolatileDemo demo = new VolatileDemo();
        Thread thread = new Thread(() -> demo.addA());
        thread.start();

        Thread.sleep(100);
        demo.flag = true;
        System.out.println("方法结束a=" + demo.a);

        Thread.sleep(100);
        System.out.println("方法结束a=" + demo.a);
    }

}
