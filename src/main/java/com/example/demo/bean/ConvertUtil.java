package com.example.demo.bean;

import net.sf.cglib.beans.BeanCopier;

/**
 * @author: administer
 * @description: 类属性工具类
 * @date: 2021/1/29  15:52
 **/
public class ConvertUtil {

    public static <S, T> void convert(S source, T target) {
        if (source == null) {
            return;
        }
        BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
        copier.copy(source, target, null);
    }

    public static <S, T> T convert(Class<T> target, S source) {
        try {
            T instance = target.getDeclaredConstructor().newInstance();
            convert(source, instance);
            return instance;
        } catch (Exception e) {
            return null;
        }
    }



}
