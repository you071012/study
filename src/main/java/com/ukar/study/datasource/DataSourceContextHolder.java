package com.ukar.study.datasource;


import com.ukar.study.datasource.enums.DataSourceEnum;

/**
 * Created by jyou on 2018/9/11.
 *
 * @author jyou
 * <p>
 * 用来设置、获取数据源连接
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setJdbcType(String jdbcType) {
        contextHolder.set(jdbcType);
    }

    public static void setSlave01() {
        setJdbcType(DataSourceEnum.Slave01.getName());
    }

    public static void setMaster() {
        setJdbcType(DataSourceEnum.Master.getName());
    }

    public static String getJdbcType() {
        String s = contextHolder.get();
        if (s == null) {
            s = DataSourceEnum.Master.getName();
        }
        return s;
    }

    public static void clearJdbcType() {
        contextHolder.remove();
    }
}
