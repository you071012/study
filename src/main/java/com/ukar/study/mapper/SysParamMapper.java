package com.ukar.study.mapper;

import com.ukar.study.entity.SysParamDO;
import org.apache.ibatis.annotations.Param;

public interface SysParamMapper {

    SysParamDO selectByParamKey(@Param("paramKey") String key);

}
