package com.example.demo.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName TreeUtil
 * @Description
 * @PackageName com.example.demo.tree.TreeUtil
 * @Author fate
 * @Date 2021/1/4  13:43
 **/
public class TreeUtil {
    /**
     * 1.两层循环实现建树
     *
     * @param treeNodes 传入的树节点列表
     * @return
     */
    public <T extends TreeNode> List<T> bulid(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<>();
        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getParentId())) {
                trees.add(treeNode);
            }
            for (T it : treeNodes) {
                if (it.getParentId() == treeNode.getId()) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.add(it);
                }
            }
        }
        return trees;
    }

    /**
     * 2.使用递归方法建树
     *
     * @param treeNodes
     * @return
     */
    public <T extends TreeNode> List<T> buildByRecursive(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<T>();
        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getParentId())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    public <T extends TreeNode> T findChildren(T treeNode, List<T> treeNodes) {
        for (T it : treeNodes) {
            if (treeNode.getId() == it.getParentId()) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

    /**
     * 3.两次遍历
     *
     * @param zoneList
     * @return
     */
    public <T extends TreeNode> List<T> buildTree3(List<T> zoneList) {
        Map<Integer, List<T>> zoneByParentIdMap = new HashMap<>();
        zoneList.forEach(zone -> {
            List<T> children = zoneByParentIdMap.getOrDefault(zone.parentId, new ArrayList<>());
            children.add(zone);
            zoneByParentIdMap.put(zone.parentId, children);

        });
        zoneList.forEach(zone -> zone.children = (List<TreeNode>) zoneByParentIdMap.get(zone.id));

        return zoneList.stream()
                .filter(v -> v.parentId == 0)
                .collect(Collectors.toList());
    }

    /**
     * 用java8的stream，三行代码实现。
     *
     * @param zoneList
     * @return
     */
    public <T extends TreeNode> List<T> buildTree4(List<T> zoneList) {
        Map<Integer, List<T>> zoneByParentIdMap = zoneList.stream().collect(Collectors.groupingBy(T::getParentId));
        zoneList.forEach(zone -> zone.children = (List<TreeNode>) zoneByParentIdMap.get(zone.id));
        return zoneList.stream().filter(v -> v.parentId == 0).collect(Collectors.toList());

    }


    private <T extends TreeNode> List<T> treeUnit(List<T> list) {
        // 便于快速查找
        Map<Integer, List<T>> childs = new HashMap<>();
        List<T> outputDTOList = new ArrayList<>();
        for (T unit : list) {
            List<T> child;

            // 创建子节点
            if ((child = childs.get(unit.id)) == null) {
                child = new ArrayList<>();
                // 便于快速查找
                childs.put(unit.id, child);
            }
            unit.children.add((TreeNode) child);

            // 父类
            if (unit.parentId == 0) {
                outputDTOList.add(unit);
            } else {
                // 子类
                // 父类为空时，先将当前值缓存，等待父父类被初始化
                childs.computeIfAbsent(unit.parentId, k -> new ArrayList<>());
                childs.get(unit.parentId).add(unit);
            }
        }

        // 返回树状结构
        return outputDTOList;
    }
}
