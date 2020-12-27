package com.example.demo.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName Contom
 * @Description
 * @PackageName com.example.demo.bean.Contom
 * @Author fate
 * @Date 2020/12/26 15:36
 **/
@Data
//public class UserDTO{
public class UserDTO extends CustomConverter<User, UserDTO> {

    private int id;

    private String name;

    private String password;

    private int age;

}
