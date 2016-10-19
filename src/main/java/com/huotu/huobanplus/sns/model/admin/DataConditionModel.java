package com.huotu.huobanplus.sns.model.admin;

import lombok.Getter;
import lombok.Setter;

/**
 * 条件查询的model
 * Created by slt on 2016/10/12.
 */
@Setter
@Getter
public class DataConditionModel {

    /**
     * 查询字段
     */
    private String name="";

    /**
     * 查询的类型
     */
    private int type;

    /**
     * 查询的值
     */
    private String value;


}
