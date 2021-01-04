package com.example.demo.multithreading;

import java.util.concurrent.Callable;

/**
 * @ClassName MyCallable
 * @Description
 * @PackageName com.example.demo.multithreading.MyCallable
 * @Author fate
 * @Date 2020/12/24  16:44
 **/
class MyCallable implements Callable<Integer> {
    private int i = 0;

    // 与run()方法不同的是，call()方法具有返回值
    @Override
    public Integer call() {
        int sum = 0;
        for (; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " MyCallable " + i);
            sum += i;
        }
        return sum;
    }

}