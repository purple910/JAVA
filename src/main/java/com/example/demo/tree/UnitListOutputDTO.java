package com.example.demo.tree;

import com.example.demo.bean.CustomConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @ClassName UnitListOutputDTO
 * @Description
 * @PackageName com.example.demo.tree.UnitListOutputDTO
 * @Author fate
 * @Date 2021/1/4  12:47
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class UnitListOutputDTO extends CustomConverter<Unit, UnitListOutputDTO> {

    /**
     * 单位编码
     */
    private String dwbm;

    /**
     * 父部门编码
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
     * 下级单位列表
     */
    private List<UnitListOutputDTO> children;
}

