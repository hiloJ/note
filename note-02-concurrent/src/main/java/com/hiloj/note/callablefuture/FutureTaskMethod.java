package com.hiloj.note.callablefuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

public class FutureTaskMethod {
    public static void main(String[] args) {
        // 初始化随机变量
        AtomicInteger integer = new AtomicInteger(0);

        // 包装Runnable
        FutureTask<AtomicInteger> futureTask = new FutureTask<>(() -> integer.compareAndSet(0, 11), integer);
        new Thread(futureTask).start();
        try {
            while(integer.get() == 0){}
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // 包装Callable接口
        FutureTask<AtomicInteger> task = new FutureTask<>(() -> {
            integer.compareAndSet(11,22);
            return integer;
        });
        new Thread(task).start();

        try {
            while(integer.get() == 11){}
            System.out.println(task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
