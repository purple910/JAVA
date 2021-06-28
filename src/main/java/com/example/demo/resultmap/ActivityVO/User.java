package com.example.demo.resultmap.ActivityVO;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private String userId;

    private String nickname;

    private String sex;

    private String headProfile;

    private Integer age;

    private Date birthTime;

    private String msg;
}
