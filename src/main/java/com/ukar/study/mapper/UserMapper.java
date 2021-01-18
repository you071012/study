package com.ukar.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ukar.study.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    void batchInsert(List<User> list);

    int updateByWhere(@Param("remark") String remark);
}
