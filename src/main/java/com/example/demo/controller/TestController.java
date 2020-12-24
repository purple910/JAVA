package com.example.demo.controller;

import com.example.demo.utils.FileRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName TestController
 * @Description 测试
 * @PackageName com.example.demo.controller.TestController
 * @Author fate
 * @Date 2020/11/3 20:08
 **/
@RestController
public class TestController {

    @Autowired
    private Environment environment;

    @GetMapping("/a")
    public StringBuilder test1() {
        StringBuilder temp = new StringBuilder();
        temp.append("<table>");
        for (int i = 1; i <= 9; i++) {
            temp.append("<tr>");
            for (int j = 1; j <= 9; j++) {
                if (i >= j) {
                    temp.append(String.format("<td>%d*%d=%d&nbsp;&nbsp;</td>", i, j, i * j));
                }
            }
            temp.append("</tr>");
        }
        temp.append("</table>");

        temp.append("<table>");
        for (int i = 9; i >= 1; i--) {
            temp.append("<tr>");
            for (int j = 1; j <= 9; j++) {
                if (i >= j) {
                    temp.append(String.format("<td>%d*%d=%d&nbsp;&nbsp;</td>", i, j, i * j));
                }
            }
            temp.append("</tr>");
        }
        temp.append("</table>");
        return temp;
    }

    @GetMapping("/file")
    public String file1(@RequestParam("path") String path){
        File file = new File(path);
        FileRunnable test = new FileRunnable(file);
        test.File();
        List<String> list = test.getContenList();
        return list.toString();
    }

    @GetMapping("/config")
    public String config(){
        StringBuffer buffer = new  StringBuffer();
        MutablePropertySources propertySources = ((StandardEnvironment) environment).getPropertySources();
        Iterator<PropertySource<?>> iterator = propertySources.iterator();
        iterator.forEachRemaining(item -> {
            buffer.append(item.getSource());
        });
        return buffer.toString();
    }

}
