package com.example.demo.customtree;

import lombok.Data;

import java.util.*;

/**
 * @author: fate
 * @description: 基础树
 * @date: 2021/6/11  12:19
 **/
@Data
public class Tree<T> {

    private T param;

    private String key = UUID.randomUUID().toString();

    private String id;

    private String parentId;

    private String name;

    private Integer label;

    private Map<String, String> slots = new HashMap<>();

    private List<Tree<T>> children = new ArrayList<>();
}
