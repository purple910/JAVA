package com.example.demo.file;

import java.io.File;

/**
 * @author: administer
 * @description:
 * @date: 2021/2/8  10:26
 **/
public class EncodingTest {

    public static void main(String[] args) {
        String javaEncode = getJavaEncode("E:\\1.txt");
        System.out.println("javaEncode = " + javaEncode);
    }

    /**
     * 得到文件的编码
     *
     * @param filePath 文件路径
     * @return 文件的编码
     */
    public static String getJavaEncode (String filePath){
        BytesEncodingDetect s = new BytesEncodingDetect();
        String fileCode = BytesEncodingDetect.javaname[s.detectEncoding(new File(filePath))];
        return fileCode;
    }
}
