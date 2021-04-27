package com.example.demo.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TreeUtils2
 * @Description
 * @PackageName com.example.demo.tree.TreeUtils2
 * @Author fate
 * @Date 2021/1/4  13:17
 **/
public class TreeUtils2 {

    public List<UnitListOutputDTO> listWithTree() {

        // 1.查询出所有的分类
        List<UnitListOutputDTO> entities = new ArrayList<>();

        // 2.组装成父子的树形结构
        List<UnitListOutputDTO> level1Menus = new ArrayList<>();

        // 找到所有的一级分类
        for (UnitListOutputDTO entity : entities) {
            if (entity.getFdwbm() == null) {
                level1Menus.add(entity);
            }
        }

        for (UnitListOutputDTO level1Menu : level1Menus) {
            level1Menu.setChildren(getChildrens(level1Menu, entities));
        }

        return level1Menus;
    }


    /**
     * 递归查找所有的下级分类
     * 在这一级别的分类中找下级分类
     *
     * @param root 当前级别的分类
     * @param all  全部分类
     * @return 下一级分类
     */
    private List<UnitListOutputDTO> getChildrens(UnitListOutputDTO root, List<UnitListOutputDTO> all) {
        List<UnitListOutputDTO> children = new ArrayList<>();
        for (UnitListOutputDTO a : all) {
            if (a.getFdwbm().equals(root.getDwbm())) {
                a.setChildren(getChildrens(a, all));
                children.add(a);
            }
        }

        return children;
    }
}
