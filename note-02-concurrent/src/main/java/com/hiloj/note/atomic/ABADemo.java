package com.hiloj.note.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 *  ABA问题及解决方案
 */
public class ABADemo {
    public static void main(String[] args) {
        // abaExample();
        abaHandle();
    }

    private static void abaHandle() {
        // 原子类型变量
        AtomicStampedReference<String> reference = new AtomicStampedReference<>("A", 1);

        new Thread(()->{
            System.out.println("线程"+Thread.currentThread().getName()+"的期望值为：" + reference.getReference());
            try {TimeUnit.SECONDS.sleep(5);} catch(InterruptedException e) {e.printStackTrace();}
            String real = reference.getReference();
            System.out.println("线程"+Thread.currentThread().getName()+"的实际值为：" + real);
            System.out.println("线程"+Thread.currentThread().getName()+"进行CAS操作");
            boolean result = reference.compareAndSet("A","B",1,2);
            System.out.println("线程"+Thread.currentThread().getName()+"数据修改是否成功：" + result + ";修改后的值为：" + reference.getReference() + "版本号为：" + reference.getStamp());
        },"Thread-1").start();

        new Thread(()->{
            try {TimeUnit.SECONDS.sleep(1);} catch(InterruptedException e) {e.printStackTrace();}
            System.out.println("线程"+Thread.currentThread().getName()+"进行ABA操作");
            System.out.println("A -----> B");
            reference.compareAndSet("A","B",1,2);
            System.out.println("B -----> A");
            reference.compareAndSet("B","A",2,3);
            System.out.println("线程"+Thread.currentThread().getName()+"结束ABA操作");
        },"Thread-2").start();
    }

    private static void abaExample() {
        // 原子类型变量
        AtomicReference<String> reference = new AtomicReference<>("A");

        new Thread(()->{
            System.out.println("线程"+Thread.currentThread().getName()+"的期望值为：" + reference.get());
            try {TimeUnit.SECONDS.sleep(5);} catch(InterruptedException e) {e.printStackTrace();}
            String real = reference.get();
            System.out.println("线程"+Thread.currentThread().getName()+"的实际值为：" + real);
            System.out.println("线程"+Thread.currentThread().getName()+"进行CAS操作");
            boolean result = reference.compareAndSet(real,"B");
            System.out.println("线程"+Thread.currentThread().getName()+"数据修改是否成功：" + result + ";修改后的值为：" + reference.get());
        },"Thread-1").start();

        new Thread(()->{
            try {TimeUnit.SECONDS.sleep(1);} catch(InterruptedException e) {e.printStackTrace();}
            System.out.println("线程"+Thread.currentThread().getName()+"进行ABA操作");
            System.out.println("A -----> B");
            reference.compareAndSet("A","B");
            System.out.println("B -----> A");
            reference.compareAndSet("B","A");
            System.out.println("线程"+Thread.currentThread().getName()+"结束ABA操作");
        },"Thread-2").start();
    }
}
