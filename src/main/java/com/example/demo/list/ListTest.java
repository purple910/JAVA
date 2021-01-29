package com.example.demo.list;

import org.junit.Test;

/**
 * @ClassName ListTest
 * @Description
 * @PackageName com.example.demo.list.ListTest
 * @Author fate
 * @Date 2021/1/15  13:53
 **/
public class ListTest {

    @Test
    public void test1() {
        //源数组
        int[] source = {10, 30, 20, 40};
        //目标数组
        int[] target = new int[source.length];
        for (int i = 0; i < source.length; i++) {
            target[i] = source[i];
        }
    }

    @Test
    public void test2() {
        int[] a = {1, 2, 3, 4};
        int[] b = {8, 7, 6, 5, 4, 3, 2, 1};
        int[] c = {10, 20};
        try {
            System.arraycopy(a, 0, b, 0, a.length);
            // 下面语句发生异常，目标数组c容纳不下原数组a的元素
            System.arraycopy(a, 0, c, 0, a.length);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e);
        }
        for (int elem : b) {
            System.out.print(elem + "  ");
        }
        System.out.println();
        for (int elem : c) {
            System.out.print(elem + "  ");
        }
        System.out.println("\n");
    }
}
