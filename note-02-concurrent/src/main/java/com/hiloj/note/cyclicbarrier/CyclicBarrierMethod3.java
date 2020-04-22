package com.hiloj.note.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CyclicBarrierMethod3 {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        new Thread(()->{
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                System.out.println("线程：" + Thread.currentThread().getName()+ "中断");
            } catch (BrokenBarrierException e) {
                System.out.println("线程：" + Thread.currentThread().getName()+ "屏障破裂");
            }
        },"t1").start();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("启动第一个线程，屏障是否破裂：" + cyclicBarrier.isBroken());

        new Thread(()->{
            try {
                cyclicBarrier.await(1,TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                System.out.println("线程：" + Thread.currentThread().getName()+ "中断");
            } catch (BrokenBarrierException e) {
                System.out.println("线程：" + Thread.currentThread().getName()+ "屏障破裂");
            } catch (TimeoutException e) {
                System.out.println("线程：" + Thread.currentThread().getName()+ "超时");
            }
        },"t2").start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("启动第二个线程，当前等待线程数量：" + cyclicBarrier.getNumberWaiting());
        System.out.println("启动第二个线程，屏障是否破裂：" + cyclicBarrier.isBroken());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("线程等待超时，当前等待线程数量：" + cyclicBarrier.getNumberWaiting());
        System.out.println("线程等待超时，屏障是否破裂：" + cyclicBarrier.isBroken());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cyclicBarrier.reset();
        System.out.println("调用reset方法，当前等待线程数量：" + cyclicBarrier.getNumberWaiting());
        System.out.println("调用reset方法，屏障是否破裂：" + cyclicBarrier.isBroken());
    }
}
