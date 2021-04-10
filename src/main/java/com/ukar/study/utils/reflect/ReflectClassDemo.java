package com.ukar.study.utils.reflect;

import com.ukar.study.entity.User;
import lombok.Data;
import lombok.ToString;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: jia.you
 * @Date: 2021/03/29/10:13
 * @Description: 主要演示获取反射对象的一些基本方法
 */
@Data
@ToString
public class ReflectClassDemo extends ReflectParent<String>{

    int num;
    public String name;
    public ReflectClassDemo() {
    }

    public ReflectClassDemo(String name) {
        this.name = name;
    }

    private ReflectClassDemo(int num) {
        this.num = num;
    }


    public void methodDemo(List<String> list){
        System.out.println(list);
    }

    public List<String> methodDemo(String str){
        System.out.println(str);
        return Arrays.asList(str);
    }

    /**
     * 获取反射对象的4种方法
     */
    public static void getClassDemo() throws Exception{
        User user = new User();

        //1：通过对象实例获取
        Class<? extends User> clazz1 = user.getClass();

        //2：通过对象类获取
        Class<User> clazz2 = User.class;

        //3：通过forName获取
        Class<?> clazz3 = Class.forName("com.ukar.study.entity.User");

        //4：通过ClassLoader获取
        ClassLoader classLoader = ReflectClassDemo.class.getClassLoader();
        Class<?> clazz4 = classLoader.loadClass("com.ukar.study.entity.User");

        System.out.println(clazz1);
        System.out.println(clazz2);
        System.out.println(clazz3);
        System.out.println(clazz4);

        System.out.println(clazz1.getConstructors()[0]);
        System.out.println(clazz1.getDeclaredConstructors()[0]);

    }

    /**
     * 反射获取构造方法
     * @throws Exception
     */
    public static void constructorsDemo() throws Exception {
        Class<ReflectClassDemo> clazz = ReflectClassDemo.class;

        //获取所有构造方法，包含私有和非私有
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        System.out.println(declaredConstructors.length);

        //获取指定无参数构造方法，私有或非私有
        Constructor<ReflectClassDemo> declaredConstructor = clazz.getDeclaredConstructor();
        declaredConstructor.setAccessible(true); //允许私有访问，测试下来可以不设置
        System.out.println(declaredConstructor);
        ReflectClassDemo reflectClassDemo = declaredConstructor.newInstance();
        reflectClassDemo.setName("nihao");
        reflectClassDemo.setNum(10);
        System.out.println(reflectClassDemo);

        //获取所有非私有构造方法
        Constructor<?>[] constructors = clazz.getConstructors();
        System.out.println(constructors.length);

        //获取指定参数非私有构造
        Constructor<ReflectClassDemo> constructor = clazz.getConstructor(String.class);
        System.out.println(constructor);
    }

    /**
     * 反射获取属性
     */
    public static void filedDemo(){
        Class<ReflectClassDemo> clazz = ReflectClassDemo.class;

        //获取所有public属性，包含当前类和父类
        Field[] fields = clazz.getFields();
        System.out.println(fields.length);

        //获取该类所有声明的私有和非私有属性
        Field[] declaredFields = clazz.getDeclaredFields();
        System.out.println(declaredFields);
    }

    /**
     * 获取类上范型类型，父类必须是范型类
     */
    public static void superClassTDemo(){
        ReflectParent reflectClassDemo = new ReflectClassDemo();
        Class<? extends ReflectParent> clazz = reflectClassDemo.getClass();
        Type genericSuperclass = clazz.getGenericSuperclass();
        //ParameterizedType 表示范型类的type类型
        if (genericSuperclass instanceof ParameterizedType){
            ParameterizedType  parameterizedType = (ParameterizedType) genericSuperclass;
            Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
            System.out.println(actualTypeArgument.getTypeName());
        }else {
            System.out.println("当前类父类上没有范型类型");
        }
    }

    /**
     * 获取方法参数上范型类型
     */
    public static void methodTDemo() throws Exception {
        ReflectParent<String> reflectClassDemo = new ReflectClassDemo();
        Class<? extends ReflectParent> clazz = reflectClassDemo.getClass();
        Method method = clazz.getDeclaredMethod("methodDemo", List.class);
        System.out.println(method);
        //获取范型参数
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        for(Type type : genericParameterTypes){
            if (type instanceof ParameterizedType){
                ParameterizedType parameterizedType = (ParameterizedType) type;
                System.out.println(parameterizedType.getActualTypeArguments()[0].getTypeName());
            }else {
                System.out.println(type.getTypeName());
            }
        }
    }

    /**
     * 获取返回值的范型类型
     * @throws NoSuchMethodException
     */
    public static void methodReturnDemo() throws NoSuchMethodException {
        ReflectParent<String> reflectClassDemo = new ReflectClassDemo();
        Class<? extends ReflectParent> clazz = reflectClassDemo.getClass();
        Method method = clazz.getDeclaredMethod("methodDemo", String.class);

        //获取返回值的范型类型
        Type type = method.getGenericReturnType();
        if (type instanceof ParameterizedType){
            ParameterizedType parameterizedType = (ParameterizedType) type;
            System.out.println(parameterizedType.getActualTypeArguments()[0].getTypeName());
        }else {
            System.out.println(type.getTypeName());
        }
    }

    public static void main(String[] args) throws Exception {
        ReflectClassDemo.methodTDemo();
    }
}
