package com.hiloj.note.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 *  家庭大扫除
 */
public class FamilyDay {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        Thread t1 = new Thread(new HouseWork("擦窗户",countDownLatch),"爸爸");
        Thread t2 = new Thread(new HouseWork("收拾厨房",countDownLatch),"妈妈");
        Thread t3 = new Thread(new HouseWork("拖地",countDownLatch),"儿子");

        try {
            t1.start();
            t2.start();
            t3.start();
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("家庭大扫除圆满成功");
    }
}
