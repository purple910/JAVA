package com.example.demo.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName FileIteration
 * @Description
 * @PackageName com.example.demo.utils.FileIteration
 * @Author 杨登柳
 * @Date 2020/12/24  12:16
 **/
public class FileIteration {

    private List<String> list = new ArrayList<>();

    public List<String> getList() {
        return list;
    }

    public void getFileName(File file){
        if (file.isDirectory()){
            File[] files = file.listFiles();
            if (files != null){
                for (int i = 0; i < files.length; i++) {
                    getFileName(files[i]);
                }
            }else {
                this.list.add(file.getPath());
                return;
            }
        }else {
            this.list.add(file.getPath());
            return;
        }
    }
}
