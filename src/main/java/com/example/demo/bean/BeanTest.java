package com.example.demo.bean;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
/**
 * @ClassName      BeanTest
 * @Description
 * @PackageName    com.example.demo.bean.BeanTest
 * @Author         fate
 * @Date           2020/12/26  13:11
**/
public class BeanTest {
    /**
     * @param source 被复制的实体类对象
     * @param to     复制完后的实体类对象
     * @throws Exception
     */
    public static void Copy(Object source, Object to) throws Exception {
        // 获取属性  
        BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(), java.lang.Object.class);
        PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();

        BeanInfo destBean = Introspector.getBeanInfo(to.getClass(), java.lang.Object.class);
        PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();

        try {
            for (PropertyDescriptor propertyDescriptor : sourceProperty) {
                for (PropertyDescriptor descriptor : destProperty) {
                    if (propertyDescriptor.getName().equals(descriptor.getName())) {
                        // 调用source的getter方法和dest的setter方法
                        descriptor.getWriteMethod().invoke(to, propertyDescriptor.getReadMethod().invoke(source));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("属性复制失败:" + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        People people = new People();
        people.setId(1);
        people.setName("root");
        people.setPassword("123");

        User user = new User();

        Copy(people, user);
        System.out.println("people = " + people);
        System.out.println("user = " + user);

    }
}