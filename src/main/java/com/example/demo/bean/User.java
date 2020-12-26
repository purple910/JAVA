package com.example.demo.bean;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName User
 * @Description
 * @PackageName com.example.demo.bean.User
 * @Author fate
 * @Date 2020/12/26 11:39
 **/
@Data
@ToString
public class User {

    private int id;

    private String name;

    private String password;

    private String sex;
}
