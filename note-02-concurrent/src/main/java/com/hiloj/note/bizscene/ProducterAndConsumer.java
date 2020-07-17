package com.hiloj.note.bizscene;

import sun.misc.VM;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 *  生产者与消费者实现的三个版本：
 *  1. wait与notify
 *  2. await与signal
 *  3. 队列
 */
public class ProducterAndConsumer {
    public static void main(String[] args) {
        ProducterAndConsumerV1 producterAndConsumerV1 = new ProducterAndConsumerV1();
        new Thread(()->{
            try {
                while (true) {
                    producterAndConsumerV1.product();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Thread-1").start();
        new Thread(()->{
            try {
                while (true) {
                    producterAndConsumerV1.consume();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"Thread-1").start();
    }
}

class ProducterAndConsumerV1 {
    private Object object = new Object();
    private String value = "";

    public void product() throws InterruptedException {
        synchronized (object){
            if (!"".equals(value)) {
                object.wait();
            }
            try {TimeUnit.SECONDS.sleep(1);} catch(InterruptedException e) {e.printStackTrace();}
            value = UUID.randomUUID().toString();
            System.out.println("set的值为：" + value);
            object.notify();
        }
    }

    public void consume() throws InterruptedException {
        synchronized (object){
            if ("".equals(value)) {
                object.wait();
            }
            try {TimeUnit.SECONDS.sleep(1);} catch(InterruptedException e) {e.printStackTrace();}
            System.out.println("get的值为：" + value);
            value = "";
            object.notify();
        }
    }
}
