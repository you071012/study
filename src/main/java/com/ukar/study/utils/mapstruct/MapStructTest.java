package com.ukar.study.utils.mapstruct;

import java.util.Date;

public class MapStructTest<T,R> {
    public static void main(String[] args) {
        Person person = new Person(1L,"zhige","zhige.me@gmail.com",new Date(),new User(18, 2L));
        User user = new User(18, 2L);
        System.out.println(MapStructUtil.INSTANCE.do2DTO(person));

        PersonDTO personDTO = MapStructUtil.INSTANCE.do2DTO(person, user);
        System.out.println(personDTO);


        Person person2 = new Person(2L,"lisi","lisi.me@gmail.com",new Date(),new User(18, 3L));
        MapStructUtil.INSTANCE.update(person2, personDTO);
        System.out.println(personDTO);

        System.out.println(MapStructUtil.INSTANCE.convert2Bool(0));

        MapStructUtil.MapStructUtil2 instance2 = MapStructUtil.INSTANCE2;
        PersonDTO personDTO1 = instance2.do2DTO(person);
        System.out.println(personDTO1.toString());
    }
}
