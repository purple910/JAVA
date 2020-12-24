package com.example.demo.pool;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ThreadPool
 * @Description
 * @PackageName com.example.demo.pool.ThreadPool
 * @Author 杨登柳
 * @Date 2020/12/24  16:56
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ThreadPool {

    /**
     * 当有新任务到来，则插入到SynchronousQueue中，由于SynchronousQueue是同步队列，因此会在池中寻找可用线程来执行，
     * 若有可以线程则执行，若没有可用线程则创建一个线程来执行该任务；若池中线程空闲时间超过指定大小，则该线程会被销毁
     */
    @Test
    public void test1() {
        /**newCachedThreadPool
         * 1.创建一个可缓存的线程池。如果线程池的大小超过了处理任务所需要的线程，那么就会回收部分空闲（60秒不执行任务）的线程<br>
         * 2.当任务数增加时，此线程池又可以智能的添加新线程来处理任务<br>
         * 3.此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小<br>
         */
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 1; i <= 10; i++) {
            final int ii = i;
            try {
                Thread.sleep(ii * 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cachedThreadPool.execute(() -> System.out.println("线程名称：" + Thread.currentThread().getName() + "，执行" + ii));
        }
    }


    /**newFixedThreadPool
     * 创建可容纳固定数量线程的池子，每隔线程的存活时间是无限的，当池子满了就不在添加线程了
     * 如果池中的所有线程均在繁忙状态，对于新任务会进入阻塞队列中(无界的阻塞队列)
     */
    @Test
    public void test2() {
        /**
         * 1.创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小<br>
         * 2.线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程<br>
         * 3.因为线程池大小为3，每个任务输出index后sleep 2秒，所以每两秒打印3个数字，和线程名称<br>
         */
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int ii = i;
            fixedThreadPool.execute(() -> {
                System.out.println("线程名称：" + Thread.currentThread().getName() + "，执行" + ii);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**newSingleThreadExecutor
     * 创建只有一个线程的线程池，且线程的存活时间是无限的；当该线程正繁忙时，对于新任务会进入阻塞队列中(无界的阻塞队列)
     */
    @Test
    public void test3() {
        /**
         *创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
         */
        ExecutorService pool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int ii = i;
            pool.execute(() -> System.out.println(Thread.currentThread().getName() + "=>" + ii));
        }
    }

    /**NewScheduledThreadPool
     * 创建一个固定大小的线程池，线程池内线程存活时间无限制，线程池可以支持定时及周期性任务执行，
     * 如果所有线程均处于繁忙状态，对于新任务会进入DelayedWorkQueue队列中，这是一种按照超时时间排序的队列结构
     */
    @Test
    public void test4(){
        /**
         * 创建一个定长线程池，支持定时及周期性任务执行。延迟执行
         */
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        Runnable r1 = () -> System.out.println("线程名称：" + Thread.currentThread().getName() + "，执行:3秒后执行");
        scheduledThreadPool.schedule(r1, 3, TimeUnit.SECONDS);
        Runnable r2 = () -> System.out.println("线程名称：" + Thread.currentThread().getName() + "，执行:延迟2秒后每3秒执行一次");
        scheduledThreadPool.scheduleAtFixedRate(r2, 2, 3, TimeUnit.SECONDS);
        Runnable r3 = () -> System.out.println("线程名称：" + Thread.currentThread().getName() + "，执行:普通任务");
        for (int i = 0; i < 5; i++) {
            scheduledThreadPool.execute(r3);
        }
    }
}
