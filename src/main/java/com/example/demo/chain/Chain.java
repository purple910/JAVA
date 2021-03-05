package com.example.demo.chain;
 
import java.util.HashMap;
import java.util.function.Function;

/**
 * 链式编程主体
 * 说明：为了实现用代码来解释代码。不需要多余的注释，特此建立此类。每一个程序以Chain.start启动，期间调用相应的操作即可。
 *
 * @author sunziwen
 * @version 1.0
 * @date 2019/6/13 9:59
 **/
public class Chain {
    /**
     * 数据基本存储容器(这里需要泛型处理)，因为java是静态语言，这里有很大的局限性！，换成python将会十分完美。
     */
    private HashMap<String, Object> container;
 
    /**
     * 链式编程起始点
     *
     * @return Chain
     */
    public static Chain start() {
        Chain chain = new Chain();
        //初始化容器
        chain.container = new HashMap<>(2);
        return chain;
    }
 
    /**
     * 链式编程终点,输出最终结果
     *
     * @return Chain
     */
    public Object end(Function<HashMap<String, Object>, Object> function) {
        return function.apply(container);
    }
 
    /**
     * 校验（必须返回true，否则抛异常，输出默认提示）
     *
     * @return Chain
     */
    public Chain must(Function<HashMap<String, Object>, Boolean> function) {
        if (!function.apply(container)) {
            throw new RuntimeException("校验未通过");
        }
        return this;
    }
 
    /**
     * 校验（必须返回true，否则抛异常，输出相应的提示）
     *
     * @return Chain
     */
    public Chain must(Function<HashMap<String, Object>, Boolean> function, String message) {
        if (!function.apply(container)) {
            throw new RuntimeException(message);
        }
        return this;
    }
 
    /**
     * 访问数据库
     *
     * @return Chain
     */
    public Chain mysql(Function<HashMap<String, Object>, Object> function) {
        container.put("mysql", function.apply(container));
        return this;
    }
 
    /**
     * 访问redis
     *
     * @return Chain
     */
    public Chain redis(Function<HashMap<String, Object>, Object> function) {
        container.put("redis", function.apply(container));
        return this;
    }
}