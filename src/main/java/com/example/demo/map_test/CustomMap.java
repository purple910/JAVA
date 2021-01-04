package com.example.demo.map_test;


import java.util.Collection;
import java.util.Set;

/**
 * @ClassName CutomMapp
 * @Description
 * @PackageName com.example.demo.map_test.CutomMapp
 * @Author fate
 * @Date 2020/11/10    10:58
 **/
public interface CustomMap<K,V> {
    int size();
    boolean isEmpty();
    boolean containsKey(Object key);
    boolean containsValue(Object value);
    V get(Object key);
    V put(K key, V value);
    V remove(Object key);
    void clear();
    @Override
    boolean equals(Object o);
    @Override
    int hashCode();

    Set<K> keySet();
    Collection<V> values();

}
