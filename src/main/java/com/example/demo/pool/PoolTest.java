package com.example.demo.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName Test
 * @Description
 * @PackageName com.example.demo.pool.Test
 * @Author fate
 * @Date 2020/12/24  17:19
 **/
public class PoolTest {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 3000; i++) {
            list.add(random.nextInt(9000) + 1000);
        }

        PoolTest test = new PoolTest();
        test.Calculation(list);
        long sum = test.getSum();
        System.out.println("sum = " + sum);
    }

    public long sum = 0L;

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    class MyRunnable implements Runnable {

        long start;
        long end;

        public MyRunnable() {
        }

        public MyRunnable(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            long temp = 0L;
            for (long i = start; i < end; i++) {
                temp += i;
            }
            sum += temp;
            System.out.println(Thread.currentThread().getName()+"\tstart:"+start+"\tend:"+end+"\tsum:"+temp);
        }
    }

    public void Calculation(List<Integer> list) {

        // 创建线程池，一共THREAD_COUNT个线程可以使用
        ExecutorService pool = Executors.newFixedThreadPool(12);

        for (int i = 1; i < list.size(); i++) {
            Runnable runnable = new MyRunnable(list.get(i - 1), list.get(i));
            pool.submit(runnable);
        }
        pool.shutdown();
        // 必须等到所有线程结束才可以让主线程退出，不然就一直阻塞
        while (!pool.isTerminated())
            ;
    }

}
