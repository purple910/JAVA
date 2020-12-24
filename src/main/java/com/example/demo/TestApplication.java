package com.example.demo;

import com.example.demo.utils.FileIteration;
import com.example.demo.utils.FileRunnable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName Test
 * @Description
 * @PackageName com.example.demo.Test
 * @Author 杨登柳
 * @Date 2020/12/23    17:22
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class TestApplication {

    @Test
    public void fileTest() {
        Date date = new Date();
        File file = new File("C:\\");
        FileRunnable test = new FileRunnable(file);
        test.File();
        System.out.println("contenList = " + test.getContenList());
        System.out.println("test = " + test.getContenList().size());

        Date date1 = new Date();
        System.out.println("date1 = " + (date1.getTime() - date.getTime()));
    }

    @Autowired
    private Environment environment;

    @Test
    public void test2(){
        System.out.println("TestApplication.test2");
        MutablePropertySources propertySources = ((StandardEnvironment) environment).getPropertySources();
        System.out.println("propertySources = " + propertySources);
        Iterator<PropertySource<?>> iterator = propertySources.iterator();
        iterator.forEachRemaining(item -> {
            System.out.println("item.getSource() = " + item.getSource());
        });
        System.out.println("environment = " + environment.toString());
    }

    @Test
    public void test3(){
        String path = "E:\\";
        File file = new File(path);

        FileIteration fileIteration = new FileIteration();
        fileIteration.getFileName(file);
        List<String> list = fileIteration.getList();
        System.out.println("list = " + list.toString());

    }

}
