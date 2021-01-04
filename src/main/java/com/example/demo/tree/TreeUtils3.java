package com.example.demo.tree;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TreeUtil
 * @Description
 * @PackageName com.example.demo.tree.TreeUtil
 * @Author fate
 * @Date 2021/1/4  11:33
 **/
public class TreeUtils3 {

    private List<UnitListOutputDTO> treeUnit(List<Unit> list, String dwbm) {
        // 便于快速查找
        Map<String, List<UnitListOutputDTO>> childs = new HashMap<>();
        List<UnitListOutputDTO> outputDTOList = new ArrayList<>();
        for (Unit unit : list) {
            UnitListOutputDTO listOutputDTO = new UnitListOutputDTO().convertIn(unit);
            List<UnitListOutputDTO> child;

            // 创建子节点
            if ((child = childs.get(unit.getDwbm())) == null) {
                child = new ArrayList<>();
                // 便于快速查找
                childs.put(unit.getDwbm(), child);
            }
            listOutputDTO.setChildren(child);

            // 父类
            if (unit.getDwbm().equals(dwbm)) {
                outputDTOList.add(listOutputDTO);
            } else {
                // 子类
                // 父类为空时，先将当前值缓存，等待父父类被初始化
                childs.computeIfAbsent(unit.getFdwbm(), k -> new ArrayList<>());
                childs.get(unit.getFdwbm()).add(listOutputDTO);
            }
        }

        // 返回树状结构
        return outputDTOList;
    }

}
