package com.huotu.huobanplus.sns.model.admin;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/10/12.
 */
@Getter
@Setter

public class AdminCategoryModel extends AdminBaseCategoryModel {
    /**
     * 排序
     */
    private Integer sort;

    private Integer parentId;
    /**
     * 父级名称
     */
    private String parentName;

    private Integer categoryType;






}
