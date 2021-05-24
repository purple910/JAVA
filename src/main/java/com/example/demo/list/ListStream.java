package com.example.demo.list;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * @ClassName ListTest
 * @Description https://blog.csdn.net/mu_wind/article/details/109516995
 * @PackageName com.example.demo.list.ListTest
 * @Author fate
 * @Date 2021/1/15  13:53
 **/
public class ListStream {

    /**
     * 1. stream的操作必须使用lambda作为参数.
     * 2. stream没有内部存储, 只是类似流水一样从数据源抓取数据.
     * 3. 对数据源进行操作但不能改变原数据,而是得到一个新的stream,并可以组成集合数据等.
     * 4. 消费性,stream只能被消费一次,消费以后就失效了,如果还需要消费,则必须重新生成stream, 即同一个stream对象只能消费一次.
     */


    @Test
    public void collect() {
        /**
         * 将流输出为指定的格式,如数组/集合等.
         */
    }

    @Test
    public void test1() {
        /**
         * filter: 按照指定的格式过滤,为true的返回
         * limit: 获取n个元素
         * skip: 跳过n个元素, 配合limit进行分页
         * distinct: 通过流元素的equals与hashCode
         */
        List<Integer> list = new ArrayList<>();
        list.add(12);
        list.add(15);
        list.add(18);
        list.add(20);
        list.add(20);
        list.add(26);
        list.add(26);
        list.stream().filter(i -> i > 14).distinct().skip(2).limit(2).forEach(System.out::println);
    }


    @Test
    public void map() {
        /**
         * 将一个流元素按照一个的规律转换为内一个元素, 适合1对1
         */
        List<User> list = new ArrayList<>();
        list.add(new User("Tom", 18));
        list.add(new User("Bob", 22));
        list.add(new User("Salary", 10));
        String s = list.stream().map(User::getName).collect(Collectors.joining(","));
        System.out.println("s = " + s);
    }

    @Test
    public void flatMap() {
        /**
         * 将一个流元素按照一个的规律转换为内一个元素, 适合1对多
         */
        List<List<Integer>> list = Arrays.asList(Collections.singletonList(1), Arrays.asList(2, 3), Arrays.asList(4, 5, 6), Arrays.asList(7, 8, 9));
        List<Integer> collect = list.stream().flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println("collect = " + collect);
    }

    @Test
    public void forEach() {

        List<User> list = new ArrayList<>();
        list.add(new User("Tom", 18));
        list.add(new User("Bob", 22));
        list.add(new User("Salary", 10));
        list.stream().forEach(item -> {
            if (item.age > 20) {
                System.out.println("item = " + item);
            }
        });
    }

    @Test
    public void sorted() {
        /**
         * 自然排序,流中元素实现Comparable接口
         */
        List<String> strings = Arrays.asList("AA", "SS", "DD", "FF", "GG", "CC");
        strings.stream().sorted().forEach(System.out::println);

        /**
         * 定制排序, 自定义Comparator排序器
         */
        // 先按名字排,在按年龄排
        List<User> list = new ArrayList<>();
        list.add(new User("Tom", 18));
        list.add(new User("Bob", 22));
        list.add(new User("Bob", 23));
        list.add(new User("Salary", 10));
        list.stream().sorted((a, b) -> {
            if (a.getName().equals(b.getName())) {
                // 22 < 23
                return a.getAge() - b.getAge();
            } else {
                return a.getName().compareTo(b.getName());
            }
        }).forEach(System.out::println);
    }

    @Test
    public void peek() {
        /**
         * 获取流中的每个元素,并对其修改
         */
        List<User> list = new ArrayList<>();
        list.add(new User("Tom", 18));
        list.add(new User("Bob", 22));
        list.add(new User("Bob", 23));
        list.add(new User("Salary", 10));
        list.stream().peek(item -> {
            item.setAge(18);
        }).forEach(System.out::println);
    }

    @Test
    public void test2() {
        /**
         * allMatch：接收一个 Predicate 函数，当流中每个元素都符合该断言时才返回true，否则返回false
         * noneMatch：接收一个 Predicate 函数，当流中每个元素都不符合该断言时才返回true，否则返回false
         * anyMatch：接收一个 Predicate 函数，只要流中有一个元素满足该断言则返回true，否则返回false
         * findFirst：返回流中第一个元素
         * findAny：返回流中的任意元素
         * count：返回流中元素的总个数
         * max：返回流中元素最大值
         * min：返回流中元素最小值
         */
        List<Integer> list = Arrays.asList(1, 5, 3, 12, 22, 15, 18, 53, 2);

        System.out.println(list.stream().allMatch(e -> e > 0));
        System.out.println(list.stream().allMatch(e -> e > 10));

        System.out.println(list.stream().noneMatch(e -> e > 10));
        System.out.println(list.stream().noneMatch(e -> e > 100));

        System.out.println(list.stream().anyMatch(e -> e > 10));
        System.out.println(list.stream().anyMatch(e -> e > 100));

        System.out.println(list.stream().findFirst().get());
        System.out.println(list.stream().findAny().get());
        System.out.println(list.stream().count());
        System.out.println(list.stream().max(Integer::compareTo).get());
        System.out.println(list.stream().min(Integer::compareTo).get());
    }

    @Test
    public void reduce() {
        /**
         *
         */
        List<Integer> list = Arrays.asList(1, 5, 3, 12, 22, 15, 18, 53, 2);
        System.out.println(list.stream().reduce((x, y) -> (x + y)).get());
        // 20 + (1, 5, 3, 12, 22, 15, 18, 53, 2)
        System.out.println(list.stream().reduce(20, (x, y) -> (x + y)));
        // 0 - (1, 5, 3, 12, 22, 15, 18, 53, 2)
        // 串行流中第二个combiner不会生效
        System.out.println(list.stream().reduce(0, (x, y) -> {
            System.out.println("x = " + x + ", y = " + y);
            return x - y;
        }, (x, y) -> {
            // 没有执行
            System.out.println("x1 = " + x + ", y1 = " + y);
            return x * y;
        }));
        // 并行流
        System.out.println(list.parallelStream().reduce(0, (x, y) -> {
            System.out.println("x = " + x + ", y = " + y);
            return x - y;
        }, (x, y) -> {
            System.out.println("x1 = " + x + ", y1 = " + y);
            return x * y;
        }));
    }

    @Test
    public void main() {
        /**
         * 对象去重(无法自定义去掉的对象,随机去除对象)
         */
        List<User> list = new ArrayList<>();
        list.add(new User("Tom", 18));
        list.add(new User("Bob", 22));
        list.add(new User("Bob", 22));
        list.add(new User("Salary", 10));
        list.add(new User("Salary", 10));
        ArrayList<User> collect = list.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(User::getName))), ArrayList::new));
        System.out.println("collect = " + collect);
        // 当name存在时, 执行后面语句
//        List<User> collect1 = list.stream().collect(Collectors.toMap(User::getName, a -> a, (x, y) -> {
//            x.setName(x.getName() + "!!");
//            return x;
//        })).values().stream().collect(Collectors.toList());
//        System.out.println("collect1 = " + collect1);
//        System.out.println("collect1 = " + collect);

        /**
         * 交集(以list1为底,跟collect比)
         */
        List<User> list1 = new ArrayList<>();
        list1.add(new User("Tom1", 18));
        list1.add(new User("Bob11", 22));
        list1.add(new User("Bob", 22));
        list1.add(new User("Salary11", 10));
        list1.add(new User("Salary", 10));
        List<User> userList = list1.stream().filter(item -> collect.stream().map(User::getName).collect(Collectors.toList()).contains(item.getName())).collect(Collectors.toList());
        System.out.println("userList = " + userList);

        /**
         * 差集(list1比collect多的)
         */
        userList = list1.stream().filter(item -> !collect.stream().map(User::getName).collect(Collectors.toList()).contains(item.getName())).collect(Collectors.toList());
        System.out.println("userList = " + userList);


        /**
         * 并集
         */
        Collection union = CollectionUtils.union(collect, list1);
        System.out.println("union = " + union);

    }


    @Data
    public static class User {
        private String name;
        private Integer age;
        private String id = UUID.randomUUID().toString();

        public User() {
        }

        public User(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
