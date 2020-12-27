package com.example.demo;

import com.example.demo.bean.CustomConverter;
import com.example.demo.bean.UserDTO;
import com.example.demo.bean.User;
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
import java.io.IOException;
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
public class ApplicationTest {

    @Test
    public void fileTest() {
        Date date = new Date();
        File file = new File("D:\\");
        FileRunnable test = new FileRunnable(file);
        test.File();
        List<String> list = test.getContenList();
        Date date1 = new Date();
        System.out.println("list = " + list);
        System.out.println("------------------------------");
        System.out.println("size = " + list.size());
        System.out.println("------------------------------");
        System.out.println("date = " + (date1.getTime() - date.getTime()));
    }

    @Autowired
    private Environment environment;

    @Test
    public void test2() {
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
    public void test3() {
        String path = "D:\\";
        File file = new File(path);

        FileIteration fileIteration = new FileIteration();
        fileIteration.getFileName(file);
        List<String> list = fileIteration.getList();
        System.out.println("list = " + list.toString());
        System.out.println();
        System.out.println("size = " + list.size());

    }

    @Test
    public void test4() throws IOException {
        String path = "E:\\admin\\Music";
        File file = new File(path);
        FileIteration fileIteration = new FileIteration();
//        fileIteration.removeSameFile(file);
        fileIteration.changeNameBySameFile(file);
    }

    @Test
    public void test5() {
        User user = new User();
        user.setId(1);
        user.setName("admin");
        user.setPassword("123456");
        user.setSex("男");

        // extends CustomConverter<User, UserDTO>
        // 1.
//        UserDTO dto = new UserDTO();
//        dto.convert(user, dto);
        // 2.
//        UserDTO dto = new UserDTO().convertByInherit(user);
        // 3.
        UserDTO dto = new CustomConverter<User, UserDTO>(UserDTO.class).convert(user);

        // 1.
//        UserDTO dto = new UserDTO();
//        new CustomConverter<User,UserDTO>().convert(user,dto);
        // 3.
//        UserDTO dto = new CustomConverter<User, UserDTO>(UserDTO.class).convert(user);




        System.out.println("user = " + user);
        System.out.println("dto = " + dto);
    }

    @Test
    public void test6() {

        UserDTO dto = new UserDTO();
        dto.setAge(18);
        dto.setId(2);
        dto.setName("root");
        dto.setPassword("11111");
        User user = new User();
//        dto.convertToDo(dto, user);

        System.out.println("user = " + user);
        System.out.println("dto = " + dto);
    }

}
