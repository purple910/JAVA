package com.example.demo.file;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author: administer
 * @description:
 * @date: 2021/2/3  9:55
 **/
public class PrintStreamTest {

    @Test
    public void test1() throws FileNotFoundException {
        // 输出流----->缓冲流----->转化流----->文件流------>文件.
        System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(new File("text.txt"), true))));
        //被输向了--->文件
        System.out.println("给理性划清界限，为自由和道德留下空间。");
        // 关闭流, 写入文件
        System.out.close();

        PrintStream io = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File("text1.txt"))));
        io.println("每一个不曾起舞的日子，都是对生命的辜负");
        io.close();
    }


    @Test
    public void test2() throws FileNotFoundException {
        // 重行转回控制台
        System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(FileDescriptor.out)), true));
        System.out.println("给理性划清界限，为自由和道德留下空间。");

        // 文件----->文件流----->转化流----->缓冲流----->输入流.
        InputStream is = new BufferedInputStream(new FileInputStream(new File("text.txt")));
        Scanner scanner = new Scanner(is);
        System.out.println(scanner.nextLine());
    }

    @Test
    public void test3() throws FileNotFoundException {
        PrintStream out = System.out;
        PrintStream PS;
        PS = new PrintStream("text2.txt");
        System.out.println("这是重定义输出文件!!");
        System.out.println("原来是输出到控制台,现在被重定义到text2.txt里面!!");
        System.out.println("现在东西都输出了,我要把输出文件该为控制台!!");
        System.setOut(out);
        System.out.println("现在应该是在控制台");
    }

}
