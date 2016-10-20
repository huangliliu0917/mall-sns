package com.huotu.huobanplus.sns.model.admin;

import lombok.Getter;
import lombok.Setter;

/**
 * 排序查询model
 * Created by slt on 2016/10/12.
 */
@Setter
@Getter
public class DataSortModel {

    /**
     * 排序字段
     */
    private String name="";

    /**
     * 排序类型：0：倒序，1：顺序
     */
    private int type;


}
