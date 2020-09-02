package com.ukar.study.lock;


import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于ReentrantLock实现一个简单的阻塞队列
 * @param <E>
 */
public class MyBlockQueue<E> {

    //阻塞队列最大容量
    private int size;

    ReentrantLock lock = new ReentrantLock();

    LinkedList<E> linkedList = new LinkedList<>();

    Condition notFull = lock.newCondition();

    Condition notEmpty = lock.newCondition();

    public MyBlockQueue(int size) {
        this.size = size;
    }

    public void put(E e){
        boolean isLock = false;
        try{
            isLock = lock.tryLock(1, TimeUnit.SECONDS);
            while (size == linkedList.size()){
                //通知在notFull条件上等待的线程休眠
                notFull.await();
            }
            linkedList.add(e);
            System.out.println("入队列：" + e);
            //通知在notEmpty条件上等待的线程唤醒
            notEmpty.signal();
        }catch (Exception ex){
            System.out.println(ex);
        }finally {
            if(isLock){
                lock.unlock();
            }
        }
    }

    public E take(){
        boolean isLock = false;

        try{
            isLock = lock.tryLock(1, TimeUnit.SECONDS);
            while (linkedList.size() == 0){
                //通知在notFull条件上等待的线程休眠
                notEmpty.await();
            }
            E e = linkedList.removeFirst();
            System.out.println("出队列：" + e);
            //通知在notEmpty条件上等待的线程唤醒
            notFull.signal();
            return e;
        }catch (Exception ex){
            System.out.println(ex);
        }finally {
            if(isLock){
                lock.unlock();
            }
        }
        return null;
    }


    public static void main(String[] args) {
        MyBlockQueue<String> myBlockQueue = new MyBlockQueue<>(10);
        for(int i = 0 ;i < 10; i++){
            String s = i + "";
            Thread t = new Thread(() -> myBlockQueue.put(s));
            t.start();
        }

        for(int i = 0 ;i < 10; i++){
            Thread t = new Thread(() -> System.out.println(myBlockQueue.take()));
            t.start();
        }

    }
}
