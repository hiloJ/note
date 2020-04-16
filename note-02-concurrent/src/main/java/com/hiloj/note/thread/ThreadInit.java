package com.hiloj.note.thread;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 *  Thread创建的六种方式：
 *  1. 继承Thread类
 *  2. 实现Runnable接口
 *  3. 实现Callable接口
 *  4. 通过定时器
 *  5. 线程池
 *  6. 通过内部类实现,lambda简写
 */
public class ThreadInit {
    public static void main(String[] args) {
        new ThreadInit1().start();
        new Thread(new ThreadInit2()).start();

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(new ThreadInit3());
        service.shutdown();

        new ThreadInit4().run();
        new ThreadInit5().run();
        new ThreadInit6().run();

    }

    /**
     *  方式一：继承Thread类
     */
    static class ThreadInit1 extends Thread{
        @Override
        public void run() {
            System.out.println("继承Thread类的多线程");
        }
    }

    /**
     *  方式二：实现Runnable接口
     */
    static class ThreadInit2 implements Runnable{

        @Override
        public void run() {
            System.out.println("实现Runnable接口的多线程");
        }
    }

    /**
     *  方式三：实现Callable接口
     */
    static class ThreadInit3 implements Callable {
        @Override
        public Integer call() {
            System.out.println("实现Callable接口的多线程");
            return 1;
        }
    }

    /**
     *  方式四：定时器实现
     */
    static class ThreadInit4 {
       public void run(){
           Timer timer = new Timer();
           timer.schedule(new TimerTask() {
               @Override
               public void run() {
                   System.out.println("通过定时器实现多线程");
               }
           },100);
           try {
               TimeUnit.SECONDS.sleep(1);
               timer.cancel();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }

    /**
     *  方式五：通过线程池实现
     */
    static class ThreadInit5 {
        public void run(){
            ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new SynchronousQueue<>());
            poolExecutor.submit(()->{
                System.out.println("通过线程池实现的多线程");
            });
            poolExecutor.shutdown();
        }
    }

    /**
     *  方式六：通过内部类实现
     */
    static class ThreadInit6 {
        public void run(){
            new Thread(()->{
                System.out.println("通过内部类实现多线程");
            }).start();
        }
    }
}
