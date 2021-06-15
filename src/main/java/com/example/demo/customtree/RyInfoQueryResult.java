package com.example.demo.customtree;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: fate
 * @description: 测试
 * @date: 2021/6/11  12:19
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class RyInfoQueryResult extends Result {

    private String jssfsc;

    private String rysfsc;

    private String ryyrhsz;

    private String fpsfsc;

    private String spjbmc;

    private String spsfsc;

    private String dwjb;
}
