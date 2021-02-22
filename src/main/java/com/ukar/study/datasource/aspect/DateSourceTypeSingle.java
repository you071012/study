package com.ukar.study.datasource.aspect;


import com.ukar.study.datasource.enums.DataSourceEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 轮询获取数据源单例类
 */
public class DateSourceTypeSingle {

    /**
     * 当前索引
     */
    private static int currentIndex = 0;

    /**
     * 总数据源个数
     */
    private static int total;

    private static List<String> list = new ArrayList<>();

    private static DateSourceTypeSingle single = new DateSourceTypeSingle();

    private DateSourceTypeSingle() {
        initData();
    }

    public static DateSourceTypeSingle getSingle() {
        return single;
    }

    /**
     * 初始化必须得参数
     */
    private static void initData() {
        DataSourceEnum[] values = DataSourceEnum.values();
        for (DataSourceEnum dataSourceEnum : values) {
            if (!DataSourceEnum.Master.equals(dataSourceEnum) &&
                    !DataSourceEnum.Slave.equals(dataSourceEnum)) {
                list.add(dataSourceEnum.name());
            }
        }
        total = list.size();
        currentIndex = total - 1;
    }


    /**
     * 为防止并发 可以在上面加锁
     *
     * @param dataSourceEnum
     * @return
     */
    public String getDataSourceEnum(DataSourceEnum dataSourceEnum) {
        if (dataSourceEnum == DataSourceEnum.Master) {
            return DataSourceEnum.Master.name();
        }

        if (dataSourceEnum != null && dataSourceEnum != DataSourceEnum.Slave) {
            return dataSourceEnum.name();
        }

        /**
         * 简单轮询获取数据源
         */
        currentIndex = (currentIndex + 1) % total;
        return list.get(currentIndex);

    }
}
