package com.example.demo.compression;

import org.apache.commons.codec.binary.Base64;

import java.util.Arrays;

/**
 * @author: fate
 * @description:
 * @date: 2021/4/25  9:01
 **/
public class base {

    /**
     * 使用org.apache.commons.codec.binary.Base64压缩字符串
     *
     * @param str 要压缩的字符串
     * @return
     */
    public static String compress(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return Base64.encodeBase64String(str.getBytes());
    }

    /**
     * 使用org.apache.commons.codec.binary.Base64解压缩
     *
     * @param compressedStr 压缩字符串
     * @return
     */
    public static String uncompress(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }
        return Arrays.toString(Base64.decodeBase64(compressedStr));
    }

}
