package com.ukar.study.jdk;

/**
 * 每个线程操作时都会有一个高速缓存区内存，当当前操作完成后，将高速缓存区内容刷新主内存中，
 * （正常情况是每个线程先从主内存中将变量拉到本地线程副本，后面该线程使用都是使用的当前副本）
 *  volatile关键字是通过缓存一致性，保证每个线程读取的变量值都是和当前主内存一致
 *
 * volatile和synchronized两者之间比较：
 * 1：关键字volatile是线程同步的轻量级实现，所以volatile性能肯定比synchronized要好；
 * 2：volatile只能修饰变量，而synchronized可以修饰方法、代码块等。
 * 3：多线程访问volatile不会发生阻塞，而synchronized会出现阻塞。
 * 4：volatile能保证数据的可见性，但不能保证数据的原子性；而synchronized可以保证原子性，也可以间接保证可见性，因为它会将私有内存和公共内存中的数据做同步处理。
 * 5：关键字volatile解决的是变量在多个线程之间的可见性；而synchronized关键字解决的是多个线程之间访问资源的同步性。
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
