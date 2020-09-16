package com.ukar.study.jdk;

import com.ukar.study.jdk.bo.UserBO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OptionalDemo {


    public static void of(){
        Optional<String> o1 = Optional.ofNullable(null);
        Optional<String> o2 = Optional.ofNullable("aa");
        System.out.println(o1.orElseGet(() -> "空"));
        System.out.println(o2.orElseGet(() -> "空"));

        System.out.println(o1.orElse("空"));
    }

    public static void map(){
        UserBO userBO = new UserBO();
        userBO.setName("ukar");
        userBO.setAge(18);

        Optional<UserBO> optional = Optional.ofNullable(userBO);
        Optional<String> os = optional.map(t -> t.getExt());
        System.out.println("map获取结果：" + os.orElse(null));

        Optional<UserBO> optional2 = Optional.ofNullable(userBO);
        Optional<String> os2 = optional2.flatMap(t -> Optional.ofNullable(t.getExt()));
        System.out.println("flatMap获取结果：" + os2.orElse(null));
    }

    public static void listCollect(){
        UserBO userBO1 = new UserBO();
        userBO1.setName("ukar1");
        userBO1.setAge(18);

        UserBO userBO2 = new UserBO();
        userBO2.setName("ukar2");
        userBO2.setAge(20);

        List<UserBO> list = new ArrayList<>();
        list.add(userBO1);
        list.add(userBO2);
        list.add(new UserBO());
        list.add(null);

        list.stream().filter(t -> t != null).forEach(t -> {
            Optional<UserBO> optional = Optional.ofNullable(t);
            //filter会筛选符合要求的Optional<UserBO>，不符合的返回Optional.empty()，注意不会返回null
            Optional<UserBO> optional1 = optional.filter(a -> a.getAge() < 20);
            System.out.println(optional1.map(a -> a.getAge()).orElse(null));
        });

    }

    public static void main(String[] args) {
//        OptionalDemo.of();
//        OptionalDemo.map();
//        OptionalDemo.listCollect();
    }
}
