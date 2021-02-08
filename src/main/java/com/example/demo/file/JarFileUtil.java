package com.example.demo.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

/**
 * @author: 杨登柳
 * @description: 读取配置文件, 添加配置规则
 * @date: 2021/2/4  11:35
 **/
public class JarFileUtil {

    public static void main(String[] args) {
        String currentJarPath = null;
        try {
            // 获取当前Jar文件名，并对其解码，防止出现中文乱码
            currentJarPath = URLDecoder.decode(com.example.demo.file.JarFileUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<String> checkWordList = JarFileUtil.getContent(currentJarPath, "application.properties");
        System.out.println("checkWordList = " + checkWordList);
    }

    /**
     * 获取jar包里面的建表与常规检查的配置
     * @param currentJarPath
     * @param path
     * @return
     */
    public static List<String> getContent(String currentJarPath, String path) {
        JarFile currentJar = null;
        try {
            currentJar = new JarFile(currentJarPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 这里写上配置文件所在的包路径
        JarEntry dbEntry = currentJar.getJarEntry(path);
        InputStream in = null;
        try {
            in = currentJar.getInputStream(dbEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 读取数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        List<String> checkWordList = new ArrayList<>();
        try {
            String s = "";
            while ((s = reader.readLine()) != null) {
                checkWordList.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //以上YourClassName是class全名，也就是包括包名
        try {
            // 读完之后一定要close 不然的话写入的时候会报错.
            reader.close();
            in.close();
            currentJar.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return checkWordList;
    }

    /**
     * 修改jar的配置文件,保存到常规检查与建表检查
     * @param finalCurrentJarPath
     * @param path
     * @param checkWordList
     */
    public static void setContent(String finalCurrentJarPath, String path, List<String> checkWordList) {
        Hashtable<String, byte[]> table = new Hashtable<String, byte[]>();
        BufferedInputStream bIn = null;
        try {
            JarFile currentJar = new JarFile(finalCurrentJarPath);
            Enumeration<JarEntry> entries = currentJar.entries();
            /* 第一步：将除了你想修改的其它的entry都保存到table中 */
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().indexOf(".") > -1) {
                    System.out.println(entry.getName());
                    if (!entry.getName().equals(path)) {
                        bIn = new BufferedInputStream(currentJar.getInputStream(entry));
                        int len = bIn.available();
                        byte[] bt = new byte[len];
                        bIn.read(bt);
                        bIn.close();
                        table.put(entry.getName(), bt);
                    }
                }
            }
            currentJar.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            JarFile currentJar = new JarFile(finalCurrentJarPath);
            Enumeration<JarEntry> entries = currentJar.entries();
            JarEntry entry = new JarEntry(path);
            JarOutputStream out = new JarOutputStream(new FileOutputStream(finalCurrentJarPath));

            /* 第二步：将你想修改的entry先写到流中 */
            out.putNextEntry(entry);
            checkWordList.forEach(item -> {
                try {
                    out.write(item.getBytes());
                    out.write(System.lineSeparator().getBytes());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            out.flush();

            /* 第三步：将保存在table中的其它entry写到流中 */
            Enumeration<String> names = table.keys();
            while (names.hasMoreElements()) {
                String entryName = names.nextElement();
                entry = new JarEntry(entryName);
                out.putNextEntry(entry);
                out.write(table.get(entryName));
                out.flush();
            }
            out.close();
            currentJar.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
