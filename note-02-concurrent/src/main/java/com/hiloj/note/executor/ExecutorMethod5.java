package com.hiloj.note.executor;

import java.util.concurrent.*;

/**
 *  将Runnable转为Callable
 */
public class ExecutorMethod5 {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);

        Callable<Object> callable1 = Executors.callable(() -> {
            System.out.println("runnable接口1");
        });

        Callable<String> callable2 = Executors.callable(() -> {
            System.out.println("runnable接口2");
        }, "success");

        Future<Object> future1 = service.submit(callable1);
        Future<String> future2 = service.submit(callable2);

        try {
            System.out.println(future1.get());
            System.out.println(future2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
