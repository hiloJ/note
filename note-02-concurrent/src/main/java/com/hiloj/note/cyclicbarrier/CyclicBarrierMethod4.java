package com.hiloj.note.cyclicbarrier;

import org.apache.commons.lang3.RandomUtils;

import java.util.Arrays;
import java.util.concurrent.*;

/**
 *  案例：多线程分组计算
 *  详细：有一个大小为5千万的随机数组，用5个线程分别计算1千万个元素的和，合并计算得出最后的结果
 */
public class CyclicBarrierMethod4 {
    public static void main(String[] args) {
        // 指定数组容量
        int size = 50000000;

        // 数组定义
        int[] nums = new int[size];

        // 添加随机数
        for (int i = 0; i < size; i++) {
            nums[i] = RandomUtils.nextInt(100,1000);
        }

        // 使用单线程计算结果
        long sum = 0L;
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            sum += nums[i];
        }
        long end = System.currentTimeMillis();
        System.out.println("单线程计算耗时：" + (end - start) + "；计算结果为：" + sum);

        // 使用多线程计算结果
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        // 定义五个容器，保存各线程计算出的结果
        final long [] result = new long[5];

        // 定义循环屏障，在屏障线程中进行结果合并
        long begin = System.currentTimeMillis();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
            long sums = 0L;
            for (int i = 0; i < 5; i++) {
                sums += result[i];
            }
            long end1 = System.currentTimeMillis();
            System.out.println("多线程计算耗时：" + (end1 - begin) + "；计算结果为：" + sums);
        });

        // 指定子数组长度
        int length = size / 5;
        // 开启五个线程进行计算
        for (int i = 0; i < 5; i++) {
            // 定义子数组
            int[] subNum = Arrays.copyOfRange(nums,(i * length),((i + 1) * length));
            // 分组计算
            int finalI = i;
            executor.submit(()->{
                for (int i1 = 0; i1 < subNum.length; i1++) {
                    result[finalI] += subNum[i1];
                }
                try {
                    // 等待其他线程进行计算
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }

        // 关闭线程池
        executor.shutdown();
    }
}
