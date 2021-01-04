package com.example.demo.tree;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TreeNode
 * @Description
 * @PackageName com.example.demo.tree.TreeNode
 * @Author fate
 * @Date 2021/1/4  13:38
 **/
@ToString
@Data
public class TreeNode {
    protected int id;
    protected int parentId;
    protected String name;
    protected List<TreeNode> children = new ArrayList<>();

    public void add(TreeNode node) {
        children.add(node);
    }
}