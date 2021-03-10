package com.ukar.study.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.ukar.study.jdk.bo.UserBO;

import java.util.Collection;
import java.util.List;

public class JSONPathUtil {
   private static String json = "{\n" +
           "    \"store\": {\n" +
           "        \"bicycle\": {\n" +
           "            \"color\": \"red\",\n" +
           "            \"price\": 19.95\n" +
           "        },\n" +
           "        \"book\": [\n" +
           "            {\n" +
           "                \"author\": \"刘慈欣\",\n" +
           "                \"price\": 8.95,\n" +
           "                \"category\": \"科幻\",\n" +
           "                \"title\": \"三体\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"author\": \"itguang\",\n" +
           "                \"price\": 12.99,\n" +
           "                \"category\": \"编程语言\",\n" +
           "                \"title\": \"go语言实战\"\n" +
           "            }\n" +
           "        ]\n" +
           "    }\n" +
           "}";

    public static void main(String[] args) {
        //获取不存在的key或值统一返回null

        //获取store下所有book对象
        JSONArray read1 = (JSONArray) JSONPath.read(json, "$.store.book");
        System.out.println("read1：" + read1);

        //获取store下第一个book对象
        JSONObject read2 = (JSONObject) JSONPath.read(json, "$.store.book[0]");
        System.out.println("read2：" + read2);

        //获取store下到索引1为止所有的book，[:-1]或[0:]表示获取所有
        List<JSONObject> read3 = (List<JSONObject>) JSONPath.read(json, "$.store.book[:1]");
        System.out.println("read3：" + read3);

        //获取所有书的作者
        List<String> read4 = (List<String>) JSONPath.read(json, "$.store.book.author");
        System.out.println("read4：" + read4);

        //获取第一本书的作者
        String read5 = (String) JSONPath.read(json, "$.store.book[0].author");
        System.out.println("read5：" + read5);

        //price大于10元的book
        List<JSONObject> read6 = (List<JSONObject>) JSONPath.read(json, "$.store.book[price > 10]");
        System.out.println("read6：" + read6);

        //price大于10元的title
        List<String> read7 = (List<String>) JSONPath.read(json, "$.store.book[price > 10].title");
        System.out.println("read7：" + read7);

        //category(类别)为科幻的book
        List<JSONObject> read8 = (List<JSONObject>) JSONPath.read(json,"$.store.book[category != '科幻']");
        System.out.println("read8：" + read8);


        //bicycle的所有属性的值
        Collection<String> read9 = (Collection<String>) JSONPath.read(json, "$.store.bicycle.*");
        System.out.println("read9：" + read9);

        //bicycle的color和price属性值
        List<String> read10 =(List<String>) JSONPath.read(json, "$.store.bicycle['author','price']");
        System.out.println("read10：" + read10);
        setTest();
    }

    public static void setTest(){
        UserBO userBO = new UserBO();
        userBO.setName("ukar");
        userBO.setAge(18);

        JSONPath.set(userBO, "$.name", "youjia");
        JSONPath.set(userBO, "$.age", 20);
        System.out.println(userBO);
    }
}
