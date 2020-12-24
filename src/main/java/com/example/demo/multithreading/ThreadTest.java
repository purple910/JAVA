package com.example.demo.multithreading;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ClassName ThreadTest
 * @Description 通过调用线程对象引用的start()方法，使得该线程进入到就绪状态，此时此线程并不一定会马上得以执行，这取决于CPU调度时机。
 * @PackageName com.example.demo.multithrea
 * ding.ThreadTest
 * @Author 杨登柳
 * @Date 2020/12/24  16:27
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ThreadTest {

    @Test
    public void test() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
            if (i == 30) {
                Thread myThread1 = new MyThread();     // 创建一个新的线程  myThread1  此线程进入新建状态
                Thread myThread2 = new MyThread();     // 创建一个新的线程 myThread2 此线程进入新建状态
                myThread1.start();                     // 调用start()方法使得线程进入就绪状态
                myThread2.start();                     // 调用start()方法使得线程进入就绪状态
            }
        }
    }

    @Test
    public void test1() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
            if (i == 30) {
                Runnable myRunnable = new MyRunnable(); // 创建一个Runnable实现类的对象
                Thread thread1 = new Thread(myRunnable); // 将myRunnable作为Thread target创建新的线程
                Thread thread2 = new Thread(myRunnable);
                thread1.start(); // 调用start()方法使得线程进入就绪状态
                thread2.start();
            }
        }
    }

    @Test
    public void test2() {
        /**
         * Thread类本身也是实现了Runnable接口，
         * 而run()方法最先是在Runnable接口中定义的方法。
         */
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
            if (i == 30) {
                Runnable myRunnable = new MyRunnable();
                Thread thread = new MyThread(myRunnable);
                thread.start();
            }
        }
    }

    @Test
    public void test3() {
        /**
         * 使用Callable和Future接口创建线程。具体是创建Callable接口的实现类，并实现clall()方法。
         * 并使用FutureTask类来包装Callable实现类的对象，且以此FutureTask对象作为Thread对象的target来创建线程
         */
        Callable<Integer> myCallable = new MyCallable();    // 创建MyCallable对象
        FutureTask<Integer> ft = new FutureTask<>(myCallable); //使用FutureTask来包装MyCallable对象
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
            if (i == 30) {
                Thread thread = new Thread(ft);     //FutureTask对象作为Thread对象的target创建新的线程
                thread.start();                      //线程进入到就绪状态
            }
        }
        System.out.println("主线程for循环执行完毕..");
        try {
            int sum = ft.get();            //取得新创建的新线程中的call()方法返回的结果
            System.out.println("sum = " + sum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}