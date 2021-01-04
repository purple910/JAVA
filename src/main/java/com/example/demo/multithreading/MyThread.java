package com.example.demo.multithreading;

/**
 * @ClassName MyThread
 * @Description 继承Thread类，重写该类的run()方法。
 * @PackageName com.example.demo.multithreading.MyThread
 * @Author fate
 * @Date 2020/12/24  16:27
 **/
class MyThread extends Thread {

    private int i = 0;

    public MyThread(){
        super();
    }

    public MyThread(Runnable target) {
        super(target);
    }

    @Override
    public void run() {
        for (i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " MyThread " + i);
        }
    }
}