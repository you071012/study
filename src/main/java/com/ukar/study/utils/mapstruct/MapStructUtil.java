package com.ukar.study.utils.mapstruct;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Date;

/**
 * 对象copy，非反射copy，编译源码会生成一个impl实现类
 * <p/>可以copy基本对象->基本对象
 * <p/>List -> List
 * <p/>Map -> Map
 * <p/>多个对象到一个对象
 */
@Mapper(imports = {Date.class})
public interface MapStructUtil{
    MapStructUtil INSTANCE = Mappers.getMapper(MapStructUtil.class);


    @Mapper
    interface MapStructUtil2{
        MapStructUtil2 INSTANCE2 = Mappers.getMapper(MapStructUtil2.class);
        PersonDTO do2DTO(Person person);
    }
    /**
     * 简单的字段相同的才会映射copy
     * @param person
     * @return
     */
    PersonDTO do2DTO(Person person);

    /**
     * 指定字段映射
     * @param person
     * @param user
     * @return
     */
    @Mappings({
            @Mapping(source = "person.birthday", target = "birth"),
            @Mapping(source = "person.birthday", target = "birthDateFormat", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(source = "user.id", target = "id"),
            @Mapping(source = "user.age", target = "personSubDTO.age"),
            @Mapping(target = "createTime", expression = "java( new Date() )")
            })
    PersonDTO do2DTO(Person person, User user);

    /**
     * 已有对象更新
     * @param person
     * @param personDTO
     * @InheritConfiguration 用于继承已有的转换方法，默认使用类为带自定义映射的
     * @MappingTarget 用于更新的对象
     */
    @InheritConfiguration(name = "do2DTO")
    void update(Person person, @MappingTarget PersonDTO personDTO);

    /**
     * 可以添加默认方法
     * @param i
     * @return
     */
    default Boolean convert2Bool(int i){
        if(i == 0){
            return false;
        }
        return true;
    }
}
