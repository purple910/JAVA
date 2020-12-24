package com.example.demo.multithreading;

/**
 * @ClassName MyRunnable
 * @Description 实现Runnable接口，并重写该接口的run()方法，该run()方法同样是线程执行体，创建Runnable实现类的实例，
 * 并以此实例作为Thread类的target来创建Thread对象，该Thread对象才是真正的线程对象。
 * @PackageName com.example.demo.multithreading.MyRunnable
 * @Author 杨登柳
 * @Date 2020/12/24  16:31
 **/
class MyRunnable implements Runnable {
    private int i = 0;

    @Override
    public void run() {
        for (i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " MyRunnable " + i);
        }
    }
}