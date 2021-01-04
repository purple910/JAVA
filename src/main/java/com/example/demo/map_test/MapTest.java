package com.example.demo.map_test;

/**
 * @ClassName Tset
 * @Description
 * @PackageName com.example.demo.Tset
 * @Author fate
 * @Date 2020/11/10    9:41
 **/
public class MapTest {
    public static void main(String[] args) {

        CustomMap<String,Object> map = new CustomHashMapImpl<>();
        map.put("name","root");
        map.put("age",18);
        map.put(null,"null");

        System.out.println(map.get("name"));
        System.out.println(map.size());
        System.out.println(map.containsKey("name"));
        System.out.println(map.containsValue("root"));
        map.clear();
        System.out.println(map.get("name"));


    }


}
