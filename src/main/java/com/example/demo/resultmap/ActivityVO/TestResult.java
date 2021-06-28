package com.example.demo.resultmap.ActivityVO;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TestResult {

    private String id;

    private List<String> thumbs;

    private Integer age;

    private Date birthday;

    private List<Tag> tagList;

    private List<Resource> resourceList;

    private Planning planning;

    private List<User> participateList;

    private Boolean sex;
}
