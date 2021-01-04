package com.example.demo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName FileIteration
 * @Description
 * @PackageName com.example.demo.utils.FileIteration
 * @Author fate
 * @Date 2020/12/24  12:16
 **/
public class FileIteration {

    private List<String> list = new ArrayList<>();

    public List<String> getList() {
        return list;
    }

    /**
     * 迭代遍历文件夹里的所有文件
     *
     * @param file
     */
    public void getFileName(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    getFileName(files[i]);
                }
            } else {
                this.list.add(file.getPath());
                return;
            }
        } else {
            this.list.add(file.getPath());
            return;
        }
    }

    /**
     * 删除当前文件里相同的文件 mp3
     *
     * @param file
     */
    public void removeSameFile(File file) throws IOException {
        File[] files = file.listFiles();
        int j = 0;
        for (int i = 1; i < files.length; i++) {
            String name = files[i].getName().split("\\.mp3")[0];
            String name1 = files[j].getName().split("\\.mp3")[0];

            if (name.contains(name1)) {
                long length = files[i].length();
                long length1 = files[j].length();
                if (length > length1) {
                    files[j].delete();
                } else if (length < length1) {
                    files[i].delete();
                }
            } else if (name1.contains(name)) {
                long length = files[i].length();
                long length1 = files[j].length();
                if (length > length1) {
                    files[j].delete();
                } else if (length < length1) {
                    files[i].delete();
                }
            }
            j++;
        }

    }

    /**
     * 改名
     *
     * @param file
     */
    public void changeNameBySameFile(File file) {
        File[] files = file.listFiles();
        for (int i = 1; i < files.length; i++) {
            if (files[i].getName().contains("(1)")) {
                System.out.println("file = " + files[i].getName());
                File f = files[i];
                String filename = f.getAbsolutePath();
                String replace = filename.replace("(1)", "");
                f.renameTo(new File(replace));
            }
        }
    }

    public void removeBlankFile(File file) {

    }
}
