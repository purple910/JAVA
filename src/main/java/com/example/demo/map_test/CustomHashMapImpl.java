package com.example.demo.map_test;

import java.io.Serializable;
import java.util.*;

/**
 * @ClassName CustomHashMap
 * @Description 自定义hashmap
 * @PackageName com.example.demo.map_test.CustomHashMap
 * @Author fate
 * @Date 2020/11/10    9:50
 **/
public class CustomHashMapImpl<K,V> implements CustomMap<K,V>, Serializable {

    private static final long serialVersionUID = 362498820763181265L;
    final float loadFactor;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private Object[] objects;
    private int length = 100;

    public CustomHashMapImpl() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.objects = new Object[length];
        for (int i = 0; i < length; i++) {
            List<Object> key = new ArrayList<>();
            List<Object> value = new ArrayList<>();
            Object[] obj = new Object[2];
            obj[0] = key;
            obj[1] = value;
            this.objects[i] = obj;
        }

    }

    @Override
    public int size() {
        int s = 0;
        for (int i = 0; i < length; i++) {
            Object[] obj = (Object[])this.objects[i];
            ArrayList list = (ArrayList) obj[0];
            s += list.size();
        }
        return s;
    }

    @Override
    public boolean isEmpty() {
        int size = size();
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        int i = key.hashCode() % length;
        Object[] obj = (Object[]) this.objects[i];
        ArrayList k = (ArrayList) obj[0];
        return k.contains(key);

    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < this.length; i++) {
            Object[] obj = (Object[]) this.objects[i];
            ArrayList k = (ArrayList) obj[1];
            boolean contains = k.contains(value);
            if(contains){
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        //允许key为null
        int index = 0;
        if (key!=null){
            index = key.hashCode() % length;
        }

        Object[] obj= (Object[])this.objects[index];
        List k = (ArrayList) obj[0];
        List v = (ArrayList) obj[1];
        boolean contains = k.contains(key);
        if(contains) {
            int indexOf = k.indexOf(key);
            V value = (V) v.get(indexOf);
            return value;
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        //允许key为null
        int index = 0;
        if (key!=null){
             index = key.hashCode() % length;
        }

        Object[] obj= (Object[])this.objects[index];
        List<Object> k = (ArrayList<Object>) obj[0];
        List<Object> v = (ArrayList<Object>) obj[1];
        // 判断是否有key
        V v1 = get(key);
        if (v1 == null) {
            k.add(key);
            v.add(value);
        }else {
            int i = k.indexOf(key);
            v.set(i, value);
        }
        obj[0] = k;
        obj[1] = v;
        this.objects[index] = obj;
        return (V) this.objects;
    }

    @Override
    public V remove(Object key) {
        //允许key为null
        int index = 0;
        if (key!=null){
            index = key.hashCode() % length;
        }

        Object[] obj= (Object[])this.objects[index];
        ArrayList<String> k = (ArrayList) obj[0];
        ArrayList<String> v = (ArrayList) obj[1];
        // 判断是否有key
        boolean contains = k.contains(key);
        if (contains) {
            int indexOf = k.indexOf(key);
            V value = (V) v.get(indexOf);
            k.remove(indexOf);
            v.remove(indexOf);
        }
        obj[0] = k;
        obj[1] = v;
        this.objects[index] = obj;
        return (V)this.objects;
    }

    @Override
    public void clear() {
        for (int i = 0; i < length; i++) {
            Object[] obj = (Object[])this.objects[i];
            obj[0] = new ArrayList<>();
            obj[1] = new ArrayList<>();
            this.objects[i] = obj;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomHashMapImpl)) {
            return false;
        }
        CustomHashMapImpl<?, ?> that = (CustomHashMapImpl<?, ?>) o;
        return Float.compare(that.loadFactor, loadFactor) == 0 &&
                Arrays.equals(objects, that.objects);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(loadFactor);
        result = 31 * result + Arrays.hashCode(objects);
        System.out.println("result = " + result);
        return result;
    }


    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (int i = 0; i < length; i++) {
            Object[] obj = (Object[]) this.objects[i];
            List key = (ArrayList) obj[0];
            for (Object o : key) {
                set.add((K) o);
            }
        }
        return set;
    }

    @Override
    public Collection<V> values() {
        Collection<V> collection = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Object[] obj = (Object[]) this.objects[i];
            List value = (ArrayList) obj[1];
            for (Object o : value) {
                collection.add((V)o);
            }
        }
        return collection;
    }


}
