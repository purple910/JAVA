package com.example.demo.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.*;

/**
 * @ClassName FileTest
 * @Description
 * @PackageName com.example.demo.utils.FileTest
 * @Author fate
 * @Date 2020/12/23    15:58
 **/
public class FileRunnable {


    // 控制线程数，最优选择是处理器线程数*3，本机处理器是4线程
    private final static int THREAD_COUNT = 12;
    // 保存文件附加信息
    private ArrayList<String> contenList = new ArrayList<>();
    // 当前文件或者目录
    private File file;

    public FileRunnable(File file) {
        this.file = file;
    }

    public FileRunnable(String path) {
        this.file = new File(path);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public List<String> getContenList() {
        return contenList;
    }

    public void setContenList(ArrayList<String> contenList) {
        this.contenList = contenList;
    }

    // 内部类继承runnable接口，实现多线程
    class FileThread implements Runnable {
        private File file;

        public FileThread(File file) {
            super();
            this.file = file;
        }

        public FileThread() {
            super();
            // TODO Auto-generated cosnstructor stub
        }

        @Override
        public void run() {
            try {
                quickFind();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // 非递归深度遍历算法
        void quickFind() throws IOException {
            // 使用栈，进行深度遍历
            Stack<File> stk = new Stack<>();
            stk.push(this.file);
            File f;
            while (!stk.empty()) {
                f = stk.pop();
                if (f.isDirectory()) {
                    File[] fs = f.listFiles();
                    if (fs != null) {
                        for (int i = 0; i < fs.length; i++) {
                            stk.push(fs[i]);
                        }
                    } else {
                        synchronized (this) {
                            contenList.add(f.getPath());
                        }
                    }
                } else if (f.isFile()) {
                    synchronized (this) {
                        contenList.add(f.getPath());
                    }
                }
            }
        }
    }

    // 深度遍历算法加调用线程池
    public void File() {
        File fl = this.file;
        ArrayList<File> flist = new ArrayList<>();
        ArrayList<File> flist2 = new ArrayList<>();
        ArrayList<File> tmp = null, next = null;
        flist.add(fl);
        // 广度遍历层数控制
        int loop = 0;
        while (loop++ < 2) {// 最优循环层数是3层，多次实验得出
            tmp = tmp == flist ? flist2 : flist;
            next = next == flist2 ? flist : flist2;
            for (int i = 0; i < tmp.size(); i++) {
                fl = tmp.get(i);
                if (fl != null) {
                    if (fl.isDirectory()) {
                        File[] fls = fl.listFiles();
                        if (fls != null) {
                            next.addAll(Arrays.asList(fls));
                        } else {
                            contenList.add(fl.getPath());
                        }
                    } else if (fl.isFile()) {
                        contenList.add(fl.getPath());
                    }
                }
            }
            tmp.clear();
        }

        // 创建线程池，一共THREAD_COUNT个线程可以使用
//        ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);
//        ExecutorService  pool = new ThreadPoolExecutor(4, 12, 0L,
//                TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<>(10),
//                Executors.defaultThreadFactory(),
//                new ThreadPoolExecutor.AbortPolicy());
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 2, 0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(next.size()),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        for (File file : next) {
            pool.submit(new FileThread(file));
//            pool.execute(new FileThread(file));
        }
        pool.shutdown();
        // 必须等到所有线程结束才可以让主线程退出，不然就一直阻塞
        while (!pool.isTerminated())
            ;
    }

}
