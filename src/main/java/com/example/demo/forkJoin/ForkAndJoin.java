package com.example.demo.forkJoin;

import java.util.concurrent.*;

/**
 * @ClassName ForkAndJoin
 * @Description 1-10000000的和
 * @PackageName cn.felord.spring.security.ForkAndJoin
 * @Author fate
 * @Date 2020/11/15 10:19
 **/
public class ForkAndJoin extends RecursiveTask<Long> {

    private static final long serialVersionUID = 1L;
    private static final  int MEMBER = 10000000;
    // 每个任务计算10000个数
    private static final int MAX = MEMBER / 8;

    private int start;
    private int end;

    ForkAndJoin(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long sum = 0;
        if (end - start <= MAX) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            int mid = start + (end - start) / 2;
            ForkAndJoin l = new ForkAndJoin(start, mid);
            ForkAndJoin r = new ForkAndJoin(mid + 1, end);
            l.fork();
            r.fork();
            long ls = l.join();
            long rs = r.join();
            sum = ls + rs;
        }
        return sum;
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long t1 = System.currentTimeMillis();
        long sum = 0;
        for (int i = 0; i <= MEMBER; i++) {
            sum += i;
        }
        long t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);
        System.out.println("sum = " + sum);

        long t3 = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkAndJoin join = new ForkAndJoin(1, MEMBER);
//        ForkJoinTask<Integer> submit = forkJoinPool.submit(join);
        Future<Long> submit = forkJoinPool.submit(join);
        System.out.println(submit.get());
        System.out.println(System.currentTimeMillis() - t3);

    }
}
