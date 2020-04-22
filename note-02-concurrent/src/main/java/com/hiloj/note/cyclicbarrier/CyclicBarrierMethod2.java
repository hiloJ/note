package com.hiloj.note.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierMethod2 {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        cyclicBarrier.reset();

        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                try {
                    cyclicBarrier.await();
                        try {
                            System.out.println("线程1打印值：i=");
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        System.out.println("调用reset方法");
        System.out.println("标志位：" + cyclicBarrier.isBroken());
        cyclicBarrier.reset();
    }
}
