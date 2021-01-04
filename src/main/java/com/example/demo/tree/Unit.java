package com.example.demo.tree;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName Unit
 * @Description
 * @PackageName com.tfswx.sms.domain.Unit
 * @Author fate
 * @Date 2020/12/27  12:49
 **/
@Data
@ToString
public class Unit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 单位编码
     */
    private String dwbm;

    /**
     * 是否删除
     */
    private String sfsc;

    /**
     * 创建时间
     */
    private Date cjsj;

    /**
     * 最后修改时间
     */
    private Date zhxgsj;

    /**
     * 父单位编码
     */
    private String fdwbm;

    /**
     * 单位名称
     */
    private String dwmc;

    /**
     * 单位简称
     */
    private String dwjc;

    /**
     * 单位全称
     */
    private String dwqc;

    /**
     * 单位属性
     */
    private String dwsx;

    /**
     * 单位级别
     */
    private String dwjb;

    /**
     * 显示顺序
     */
    private String xh;


}
