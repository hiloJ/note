package com.hiloj.note.callablefuture;

import java.util.concurrent.*;

public class FutureMethod {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(1);
        Callable<Integer> callable = ()->{
            // 模拟程序计算耗时
            Thread.sleep(5000);
            return 1000;
        };

        // 任务提交
        Future<Integer> future = service.submit(callable);

        try {
            Thread.sleep(1000);
            System.out.println("等待1秒，计算是否完成" + future.isDone());
            System.out.println("等待1秒，计算是否取消" + future.isCancelled());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        future.cancel(true);
        try {
            Thread.sleep(1000);
            System.out.println("调用cancel(true)，计算是否完成" + future.isDone());
            System.out.println("调用cancel(true)，计算是否取消" + future.isCancelled());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 再次提交callable
        Future<Integer> future1 = service.submit(callable);

        try {
            Integer integer = future1.get(2, TimeUnit.SECONDS);
            System.out.println("尝试2秒内获取计算结果为：" + integer);
        } catch (InterruptedException e) {
            System.out.println("尝试2秒内获取计算结果，线程中断" );
        } catch (ExecutionException e) {
            System.out.println("尝试2秒内获取计算结果，线程池异常" );
        } catch (TimeoutException e) {
            System.out.println("尝试2秒内获取计算结果，等待时间超时" );
        }

        service.shutdown();
    }
}
