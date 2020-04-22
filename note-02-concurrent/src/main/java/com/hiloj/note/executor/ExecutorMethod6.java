package com.hiloj.note.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 *  线程工厂
 */
public class ExecutorMethod6 {
    public static void main(String[] args) {
        ThreadFactory factory = Executors.defaultThreadFactory();
        for (int i = 0; i < 5; i++) {
            factory.newThread(()->{
                System.out.println(Thread.currentThread());
            }).start();
        }

        ThreadFactory threadFactory = Executors.privilegedThreadFactory();
        for (int i = 5; i < 10; i++) {
            threadFactory.newThread(()->{
                System.out.println(Thread.currentThread());
            }).start();
        }
    }
}