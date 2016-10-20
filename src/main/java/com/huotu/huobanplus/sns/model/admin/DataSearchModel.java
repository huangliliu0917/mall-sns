package com.huotu.huobanplus.sns.model.admin;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 数据查询model
 * Created by slt on 2016/10/12.
 */
@Setter
@Getter
public class DataSearchModel {

    /**
     * 查询实体名称
     */
    private String name="";

    /**
     * 第几页
     */
    private int pageNo=0;

    /**
     * 每页数量
     */
    private int pageSize=10;

    /**
     * 条件查询列表
     */
    private List<DataConditionModel> conditions;

    /**
     * 排序字段列表
     */
    private List<DataSortModel> sorts;

}
