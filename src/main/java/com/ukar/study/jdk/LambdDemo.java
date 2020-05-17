package com.ukar.study.jdk;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.ukar.study.jdk.bo.UserBO;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author jia.you
 * @date 2020/03/25
 */
public class LambdDemo {

    /**
     * 排序
     */
    public static void listSort(){
        UserBO userBO1 = new UserBO("lili", 18);
        UserBO userBO2 = new UserBO("hanmeimei", 20);

        List<UserBO> list = Lists.newArrayList();
        list.add(userBO1);
        list.add(userBO2);

        //按照age升序，写法一
        list.sort(Comparator.comparing(UserBO::getAge));

        //按照age升序，写法二
        list.sort((UserBO u1, UserBO u2) -> u1.getAge() - u2.getAge());

        //按照age升序，写法三
        list.sort(Comparator.comparing(u -> u.getAge()));

        list.forEach(t -> System.out.println("age升序：" + t));

        //按照age降序
        list.sort((UserBO u1, UserBO u2) -> u2.getAge() - u1.getAge());
        list.forEach(t -> System.out.println("age降序：" + t));
    }

    public static void reduce(){
        List<Integer> list = Arrays.asList(1,3,9,7,2);
        Integer sum = list.stream().filter(t -> t < 5).reduce(0, (a, b) -> a + b);
        System.out.println("sum：" + sum);
        Integer max = list.stream().reduce((a, b) -> a > b ? a : b).orElse(null);
        System.out.println("max：" + max);
        Integer min = list.stream().reduce((a, b) -> a < b ? a : b).orElse(null);
        System.out.println("min：" + min);
    }

    /**
     * 规约汇总
     */
    public static void collect(){
        List<String> list = Stream.of("a","b","c").collect(Collectors.toList());
        //计数统计
        Long count = list.stream().collect(Collectors.counting());
        System.out.println("count：" + count);

        //字符串拼接，可指定分隔符，内部使用StringBuild拼接
        String join = list.stream().collect(Collectors.joining("&"));
        System.out.println("join：" + join);

        Consumer<String> consumer = (i) -> list.add(i);
        List<Consumer<String>> consumerList = Lists.newArrayList();
    }

    public static void groupby(){
        UserBO userBO1 = new UserBO("ukar1", 18);
        UserBO userBO2 = new UserBO("ukar2", 20);
        UserBO userBO3 = new UserBO("ukar3", 19);
        List<UserBO> list = Arrays.asList(userBO1, userBO2, userBO3);

        //转map
        Map<String, UserBO> map1 = list.stream().collect(Collectors.toMap(UserBO::getName, t -> t));
        Set<Map.Entry<String, UserBO>> set = map1.entrySet();
        set.stream().forEach(t -> {
            System.out.println(t.getKey());
            System.out.println(JSON.toJSONString(t.getValue()));
        });

        //默认形式分组
        Map<Integer, List<UserBO>> map2 = list.stream().collect(Collectors.groupingBy(UserBO::getAge));

        //多级分组
        Map<Integer, Map<String, List<UserBO>>> map3 = list.stream().collect(Collectors.groupingBy(UserBO::getAge, Collectors.groupingBy(UserBO::getName)));
        Map<Integer, Long> map4 = list.stream().collect(Collectors.groupingBy(UserBO::getAge, Collectors.counting()));

    }

    public static void foreachTest(){
        List<String> list = Arrays.asList("A","B","C");
        IntStream.range(0, list.size()).forEach(t -> System.out.println(list.get(t)));
    }

    /**
     * 自定义函数式方法测试
     */
    private static void userProcess(UserProcess userProcess){
        UserBO userBO = new UserBO("ukar", 18);
        System.out.println(userProcess.process(userBO));
    }

    public static void main(String[] args) {
//        LambdDemo.listSort();
//        LambdDemo.userProcess(UserBO::getName);
//        LambdDemo.reduce();
//        LambdDemo.collect();
//        LambdDemo.groupby();
//        LambdDemo.foreachTest();
    }
}
