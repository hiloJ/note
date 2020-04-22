package com.hiloj.note.key_words;

/**
 * Volatile关键字保证可见性及有序性，不保证原子性
 * 一般应用场景有以下几类：
 *  1. 状态标识
 *  2. 一次性安全发布(双重检查锁)
 * @author hiloj
 * @create 2020-04-20 21:24
 */
public class VolatileMethod {
    public static void main(String[] args) {
        StatusFlag statusFlag = new StatusFlag();
        statusFlag.workAndStop();

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                System.out.println(DoubleCheckLock.getInstance());
            },"Thread-" + i).start();
        }
    }
}

/**
 *  场景一：状态标识
 */
class StatusFlag{
    private boolean flag = true;

    public void startWork(){
        System.out.println("工人开始工作");
        while (flag){}
        System.out.println("工人的结束工作");
    }

    public void stopWork(){
        flag = false;
    }

    public void workAndStop(){
        new Thread(()->{
            startWork();
        },"Thread-A").start();

        try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}

        new Thread(()->{
            stopWork();
            System.out.println("线程[" + Thread.currentThread().getName() + "] \t 修改了状态值为：" + flag);
        },"Thread-B").start();
    }
}

/**
 *  双重检查锁定
 *  原因：在缺乏同步的情况下，可能会遇到某个对象引用的更新值和该对象的旧值共存
 */
class DoubleCheckLock{
    private static volatile DoubleCheckLock doubleCheckLock = null;

    private DoubleCheckLock(){}

    public static DoubleCheckLock getInstance(){
        if (doubleCheckLock == null) {
            synchronized (DoubleCheckLock.class){
                if (doubleCheckLock == null) {
                    doubleCheckLock = new DoubleCheckLock();
                }
            }
        }
        return doubleCheckLock;
    }
}