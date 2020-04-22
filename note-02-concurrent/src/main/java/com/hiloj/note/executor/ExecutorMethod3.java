package com.hiloj.note.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 *  invokeAll 与 invokeAny的区别
 */
public class ExecutorMethod3 {
    public static void main(String[] args) {
        // 初始化callable容器
        List<Callable<Integer>> callables = new ArrayList<>(5);
        // 添加callable实例
        for (int i = 0; i < 5; i++) {
            callables.add(() -> {
                int num = ThreadLocalRandom.current().nextInt(100, 2000);
                Thread.sleep(num);
                System.out.println("随机睡眠时间为：" + num);
                return num;
            });
        }

        // 初始化1个执行器
        ExecutorService service = Executors.newFixedThreadPool(1);

        int type = 2;
        switch (type){
            case 1:
                try {
                    // 获取所有的结果,所有任务均执行
                    List<Future<Integer>> futures = service.invokeAll(callables);
                    futures.forEach(f -> {
                        try {
                            System.out.println("invokeAll():" + f.get());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    // 获取任意一个执行完成的结果,其他任务不再执行
                    Integer integer = service.invokeAny(callables);
                    System.out.println("invokeAny" + integer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                break;
            default:break;
        }

        // 关闭执行器
        service.shutdown();
    }
}