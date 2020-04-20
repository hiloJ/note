package com.hiloj.note.keywords;

import sun.awt.SunDisplayChanger;

/**
 * Synchronized的几种用法：
 *  1. 同步代码块
 *      1.1 本地变量加锁
 *      1.2 类静态变量加锁
 *      1.3 共享变量加锁
 *      1.4 类对象加锁
 *  2. 同步方法
 *      2.1 修饰普通方法
 *      2.2 修饰类静态方法
 * @author hiloj
 * @create 2020-04-20 23:04
 */
public class SynchronizedMethod {
    public static void main(String[] args) {
        SynchronizedCodeBlock1 synchronizedCodeBlock1 = new SynchronizedCodeBlock1();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                synchronizedCodeBlock1.add();
            },"Thread-" + i);
        }
        try {Thread.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("计算结果为：" + synchronizedCodeBlock1.get());
    }
}

class SynchronizedCodeBlock1{
    private Object object = new Object();
    int i = 0;

    public void add(){
        synchronized (object){
            for (int i1 = 0; i1 < 10; i1++) {
                i++;
            }
        }
    }

    public Integer get(){return this.i;}
}
