package com.example.demo.customtree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: fate
 * @description:
 * @date: 2021/6/11  15:08
 **/
public class TreeUtil {

    /**
     * 1 单位
     * 2 部门
     * 3 角色
     * 4 人员
     *
     * @param list
     * @return
     */
    public static <T> List<Tree<T>> tree(List<RyInfoQueryResult> list, Class<T> cl) {
        List<Tree<T>> trees = new ArrayList<>();
        Map<String, Tree<T>> dwMap = new HashMap<>(10);
        Map<String, Tree<T>> bmMap = new HashMap<>(45);
        Map<String, Tree<T>> jsMap = new HashMap<>(100);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDwbm() != null && !"".equals(list.get(i).getDwbm()) && dwMap.get(list.get(i).getDwbm()) == null) {
                Tree<T> t = new Tree<>();
                t.setId(list.get(i).getDwbm());
                t.setName(list.get(i).getDwmc());
                t.setLabel(1);
                if (list.get(i).getFdwbm() != null) {
                    t.setParentId(list.get(i).getFdwbm());
                }
                t.getSlots().put("icon", "1");
                if (cl!=null) {
                    T t1 = ConverterUtils.convert(list.get(i), cl);
                    t.setParam(t1);
                }
                dwMap.put(list.get(i).getDwbm(), t);
            }

            if (list.get(i).getBmbm() != null) {
                if (bmMap.get(list.get(i).getDwbm() + "-" + list.get(i).getBmbm()) == null) {
                    Tree<T> t = new Tree<>();
                    t.setId(list.get(i).getDwbm() + "-" + list.get(i).getBmbm());
                    t.setName(list.get(i).getBmmc());
                    t.setLabel(2);
                    if (list.get(i).getFbmbm() != null) {
                        t.setParentId(list.get(i).getDwbm() + "-" + list.get(i).getFbmbm());
                    }
                    t.getSlots().put("icon", "2");
                    if (cl!=null) {
                        T t1 = ConverterUtils.convert(list.get(i), cl);
                        t.setParam(t1);
                    }
                    bmMap.put(list.get(i).getDwbm() + "-" + list.get(i).getBmbm(), t);
                }
            }

            if (list.get(i).getJsbm() != null) {
                if (jsMap.get(list.get(i).getDwbm() + "-" + list.get(i).getBmbm() + "-" + list.get(i).getJsbm()) == null) {
                    Tree<T> t = new Tree<>();
                    t.setId(list.get(i).getDwbm() + "-" + list.get(i).getBmbm() + "-" + list.get(i).getJsbm());
                    t.setName(list.get(i).getBmmc());
                    t.setLabel(3);
                    t.getSlots().put("icon", "3");
                    if (cl!=null) {
                        T t1 = ConverterUtils.convert(list.get(i), cl);
                        t.setParam(t1);
                    }
                    jsMap.put(list.get(i).getDwbm() + "-" + list.get(i).getBmbm() + "-" + list.get(i).getJsbm(), t);
                }
                if (list.get(i).getRybm() != null) {
                    Tree<T> role = jsMap.get(list.get(i).getDwbm() + "-" + list.get(i).getBmbm() + "-" + list.get(i).getJsbm());
                    Tree<T> t = new Tree<>();
                    t.setId(list.get(i).getDwbm() + "-" + list.get(i).getBmbm() + "-" + list.get(i).getJsbm() + "-" + list.get(i).getRybm());
                    t.setName(list.get(i).getRymc());
                    t.setLabel(4);
                    t.getSlots().put("icon", "4");
                    if (cl!=null) {
                        T t1 = ConverterUtils.convert(list.get(i), cl);
                        t.setParam(t1);
                    }
                    role.getChildren().add(t);
                    jsMap.put(list.get(i).getDwbm() + "-" + list.get(i).getBmbm() + "-" + list.get(i).getJsbm(), role);
                }
            }
        }

        //
        if (!jsMap.keySet().isEmpty()) {
            for (String key : jsMap.keySet()) {
                Tree<T> role = jsMap.get(key);
                String roleId = role.getId();
                String[] split = roleId.split("-");
                Tree<T> dept = bmMap.get(split[0] + "-" + split[1]);
                dept.getChildren().add(role);
            }
        }

        //
        if (!bmMap.keySet().isEmpty()) {
            for (String key : bmMap.keySet()) {
                Tree<T> dept = bmMap.get(key);
                if (dept.getParentId() != null) {
                    String parentId = dept.getParentId();
                    if (parentId != null) {
                        Tree<T> parent = bmMap.get(parentId);
                        parent.getChildren().add(dept);
                    }
                }
            }
            List<String> collect = new ArrayList<>(bmMap.keySet());
            for (int i = 0; i < collect.size(); i++) {
                Tree<T> dept = bmMap.get(collect.get(i));
                if (dept.getParentId() != null) {
                    bmMap.remove(collect.get(i));
                } else {
                    String deptId = dept.getId();
                    String[] split = deptId.split("-");
                    Tree<T> unit = dwMap.get(split[0]);
                    unit.getChildren().add(dept);
                }
            }
        }

        if (!dwMap.keySet().isEmpty()) {
            for (String key : dwMap.keySet()) {
                Tree<T> unit = dwMap.get(key);
                if (unit.getParentId() != null) {
                    String parentId = unit.getParentId();
                    if (parentId != null) {
                        Tree<T> parent = dwMap.get(parentId);
                        parent.getChildren().add(unit);
                    }
                }
            }
            List<String> collect = new ArrayList<>(dwMap.keySet());
            for (int i = 0; i < collect.size(); i++) {
                Tree<T> unit = dwMap.get(collect.get(i));
                if (unit.getParentId() == null) {
                    trees.add(unit);
                }
            }
        }


        return trees;
    }
}
