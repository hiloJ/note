package com.hiloj.note.keywords;

import java.util.concurrent.TimeUnit;

/**
 * Synchronized的几种用法：
 * 1. 同步代码块
 *  1.1 本地变量加锁
 *  1.2 类静态变量加锁
 *  1.3 共享变量加锁
 *  1.4 类对象加锁
 * 2. 同步方法
 *  2.1 修饰普通方法
 *  2.2 修饰类静态方法
 *
 * @author hiloj
 * @create 2020-04-20 23:04
 */
public class SynchronizedMethod {
    public static void main(String[] args) {
        new SynchronizedCodeBlock1().call();

        new SynchronizedCodeBlock2().call();

        new SynchronizedCodeBlock3(new Object()).call();

        new SynchronizedCodeBlock4().call();

        new SynchronizedMethod1().call();

        new SynchronizedMethod2().call();
    }
}

/**
 *  同步方法：修饰静态方法
 */
class SynchronizedMethod2{
    private static int number;
    public static synchronized void add(){
        for (int i = 0; i < 10; i++) {
            number++;
        }
    }

    public void call(){
        for (int i = 0; i < 10; i++) {
            new Thread(()->{add();},"Thread-" +i).start();
        }
        try {TimeUnit.SECONDS.sleep(1);} catch(InterruptedException e) {e.printStackTrace();}

        System.out.println("修饰静态方法，计算后的值为：" + number);
    }
}

/**
 *  同步方法：修饰普通方法
 */
class SynchronizedMethod1{
    private int number;
    public synchronized void add(){
        for (int i = 0; i < 10; i++) {
            number++;
        }
    }

    public void call(){
        for (int i = 0; i < 10; i++) {
            new Thread(()->{add();},"Thread-" +i).start();
        }
        try {TimeUnit.SECONDS.sleep(1);} catch(InterruptedException e) {e.printStackTrace();}

        System.out.println("修饰普通方法，计算后的值为：" + number);
    }
}


/**
 *  锁对象：类对象
 */
class SynchronizedCodeBlock4 {
    private int number = 0;

    public void add(){
        synchronized (SynchronizedCodeBlock4.class){
            for (int i = 0; i < 10; i++) {
                number++;
            }
        }
    }

    public void call(){
        for (int i = 0; i < 10; i++) {
            new Thread(()->{add();},"Thread-" +i).start();
        }
        try {TimeUnit.SECONDS.sleep(1);} catch(InterruptedException e) {e.printStackTrace();}

        System.out.println("加锁对象为类对象，计算后的值为：" + number);
    }
}

/**
 *  锁对象：共享变量
 */
class SynchronizedCodeBlock3 {
    private Object object;
    private int number = 0;

    public SynchronizedCodeBlock3(Object object){
        this.object = object;
    }

    public void add(){
        synchronized (object){
            for (int i = 0; i < 10; i++) {
                number++;
            }
        }
    }

    public void call(){
        for (int i = 0; i < 10; i++) {
            new Thread(()->{add();},"Thread-" +i).start();
        }
        try {TimeUnit.SECONDS.sleep(1);} catch(InterruptedException e) {e.printStackTrace();}

        System.out.println("加锁对象为共享变量，计算后的值为：" + number);
    }
}

/**
 *  锁对象：静态变量
 */
class SynchronizedCodeBlock2 {
    private static Object object = new Object();
    private int number = 0;

    public void add(){
        synchronized (object){
            for (int i = 0; i < 10; i++) {
                number++;
            }
        }
    }

    public void call(){
        for (int i = 0; i < 10; i++) {
            new Thread(()->{add();},"Thread-" +i).start();
        }
        try {TimeUnit.SECONDS.sleep(1);} catch(InterruptedException e) {e.printStackTrace();}

        System.out.println("加锁对象为类静态变量，计算后的值为：" + number);
    }
}

/**
 *  锁对象：本地变量
 */
class SynchronizedCodeBlock1 {
    private Object object = new Object();
    private int number = 0;

    public void add() {
        synchronized (object) {
            for (int i = 0; i < 10; i++) {
                number += 1;
            }
        }
    }

    public void call() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                add();
            }, "Thread-" + i).start();
        }
        try {TimeUnit.SECONDS.sleep(1);} catch(InterruptedException e) {e.printStackTrace();}
        System.out.println("加锁对象为本地变量，计算后的值为：" + number);
    }
}