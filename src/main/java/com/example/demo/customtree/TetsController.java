package com.example.demo.customtree;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: fate
 * @description: test
 * @date: 2021/6/15  10:25
 **/
@RestController
@RequestMapping
public class TetsController {

//    @Autowired
//    private ZzjgXtRybmMapper zzjgXtRybmMapper;

    @PostMapping(value = "/queryByDwbm")
    public List<Tree<Custom>> queryByDwbm(@RequestParam String dwbm) {
//        List<RyInfoQueryResult> list = zzjgXtRybmMapper.queryByDwbm1(queryParam);
//        List<Tree<Custom>> trees = TreeUtil.tree(list, Custom.class);
        return null;
    }
}
