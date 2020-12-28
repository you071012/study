package com.ukar.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

@TableName(value = "t_user")
@Data
public class User {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "name")
    private String name;

    private String remark;

    @TableField(value = "create_time")
    private LocalDateTime localDateTime;

    public static void main(String[] args) {
        LocalDate date1 = LocalDate.of(2020, 1, 2);
        LocalDate date2 = LocalDate.of(2020, 1, 10);
        System.out.println(dateDiff(date1,date2));
    }

    public static int dateDiff(LocalDate dt1,LocalDate dt2){
        long l1 = dt1.toEpochDay();
        long l2 = dt2.toEpochDay();
        return (int) (l1 - l2);
    }
}
