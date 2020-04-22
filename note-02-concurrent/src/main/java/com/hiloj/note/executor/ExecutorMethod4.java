package com.hiloj.note.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class ExecutorMethod4 {
    public static void main(String[] args) {
        AtomicInteger integer = new AtomicInteger(1);
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

        service.schedule(()->{
            System.out.println("schedule()延迟1秒执行");
        },1,TimeUnit.SECONDS);

        service.scheduleWithFixedDelay(()->{
            int num = integer.get();
            System.out.println("scheduleWithFixedDelay() 执行第个"+num+"任务");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("scheduleWithFixedDelay() 第"+num+"个任务执行完成");
            integer.getAndIncrement();
        },0,2,TimeUnit.SECONDS);


        service.scheduleAtFixedRate(()->{
            int num = integer.get();
            System.out.println("scheduleAtFixedRate() 执行第个"+num+"任务");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("scheduleAtFixedRate() 第"+num+"个任务执行完成");
            integer.getAndIncrement();
        },0,2,TimeUnit.SECONDS);
    }
}
