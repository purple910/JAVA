package com.example.demo.customtree;

import org.springframework.cglib.beans.BeanCopier;

/**
 * @author: fate
 * @description: 对象属性值复制
 * @date: 2021/4/15  15:17
 **/
public class ConverterUtils {
    private ConverterUtils() {
    }

    public static <S, T> void convert(S source, T target) {
        if (source == null) {
            return;
        }
        BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
        copier.copy(source, target, null);
    }


    public static <S, T> T convert(S source, Class<T> target) {
        if (source == null) {
            return null;
        }
        BeanCopier copier = BeanCopier.create(source.getClass(), target, false);
        T instance = null;
        try {
            instance = target.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        copier.copy(source, instance, null);
        return instance;
    }

}
