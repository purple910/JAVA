package com.example.demo.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TreeUtils1
 * @Description
 * @PackageName com.example.demo.tree.TreeUtils1
 * @Author fate
 * @Date 2021/1/4  12:33
 **/
public class TreeUtils1 {

    List<UnitListOutputDTO> TreeNodes = new ArrayList<>();

    public List buildTree(List<UnitListOutputDTO> TreeNodes) {
        TreeUtils1 treeBuilder = new TreeUtils1(TreeNodes);
        return treeBuilder.buildJSONTree();
    }

    public TreeUtils1() {
    }

    public TreeUtils1(List<UnitListOutputDTO> TreeNodes) {
        super();
        this.TreeNodes = TreeNodes;
    }

    /**
     * 构建JSON树形结构
     *
     * @return
     */
    public List<UnitListOutputDTO> buildJSONTree() {
        List<UnitListOutputDTO> TreeNodeTree = buildTree();
        return TreeNodeTree;
    }

    /**
     * 构建树形结构
     *
     * @return
     */
    public List<UnitListOutputDTO> buildTree() {
        List<UnitListOutputDTO> treeTreeNodes = new ArrayList<>();
        //获取所有根节点
        List<UnitListOutputDTO> rootTreeNodes = getRootTreeNodes();
        //获取每个根节点
        for (UnitListOutputDTO rootTreeNode : rootTreeNodes) {
            try {
                //递归这个根节点的子节点
                buildChildTreeNodes(rootTreeNode);

                //用list来存放每个根节点
                treeTreeNodes.add(rootTreeNode);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return treeTreeNodes;
    }

    /**
     * 递归子节点
     *
     * @param TreeNode
     */
    public void buildChildTreeNodes(UnitListOutputDTO TreeNode) {
        List<UnitListOutputDTO> children = getChildTreeNodes(TreeNode);
        if (!children.isEmpty()) {
            for (UnitListOutputDTO child : children) {
                try {
                    buildChildTreeNodes(child);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            TreeNode.setChildren(children);
        }
    }

    /**
     * 获取父节点下所有的子节点
     *
     * @param pTreeNode
     * @return
     */
    public List<UnitListOutputDTO> getChildTreeNodes(UnitListOutputDTO pTreeNode) {
        List<UnitListOutputDTO> childTreeNodes = new ArrayList<>();

        for (UnitListOutputDTO n : TreeNodes) {
            //  if (pTreeNode.getCode().equals(n.getPcode())) {
            if (pTreeNode.getDwbm().equals(n.getFdwbm())) {
                childTreeNodes.add(n);
            }
        }
        return childTreeNodes;
    }

    /**
     * 判断是否为根节点
     *
     * @param TreeNode
     * @return
     */
    public boolean rootTreeNode(UnitListOutputDTO TreeNode) {
        boolean isRootTreeNode = true;
        for (UnitListOutputDTO n : TreeNodes) {
            // if (TreeNode.getPcode().equals(n.getCode())) {
            if (TreeNode.getFdwbm().equals(n.getDwbm())) {
                isRootTreeNode = false;
                break;
            }
        }
        return isRootTreeNode;
    }

    /**
     * 获取集合中所有的根节点
     *
     * @return
     */
    public List<UnitListOutputDTO> getRootTreeNodes() {
        List<UnitListOutputDTO> rootTreeNodes = new ArrayList<>();
        for (UnitListOutputDTO n : TreeNodes) {
            if (rootTreeNode(n)) {
                rootTreeNodes.add(n);
            }
        }
        return rootTreeNodes;
    }
}