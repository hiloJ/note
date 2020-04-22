package com.hiloj.note.callablefuture;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  获取随机数三种方式：
 *  1. Runnable + Thread
 *  2. Runnable + Executor
 *  3. Callable + Future + Executor
 */
public class GetRandom {
    public static void main(String[] args) {
        // 初始化随机变量
        AtomicInteger integer = new AtomicInteger();

        // Runnable接口
        Runnable runnable = () -> {
            // 获取随机数，赋值给随机变量，供外部使用
            integer.compareAndSet(integer.get(), ThreadLocalRandom.current().nextInt(1000));
            System.out.println("线程"+ Thread.currentThread().getName()+"获取到的随机数为：" + integer.get());
        };

        // 通过Thread打印随机数
         new Thread(runnable,"thread").start();

        // 创建线程池
        ExecutorService service = Executors.newFixedThreadPool(1);
        // 通过Executor打印随机数
        service.submit(runnable);



        // Callable接口
        Callable<Integer> callable = ()->ThreadLocalRandom.current().nextInt(100,1000);
        // Executor调用callable,Future接收返回参数
        Future<Integer> future = service.submit(callable);
        try {
            System.out.println("future获取到的值为：" + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // 关闭线程池
        service.shutdown();
    }
}
