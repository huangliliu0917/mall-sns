package com.huotu.huobanplus.sns.model.admin;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/10/12.
 */
@Getter
@Setter
public class AdminCategoryModel {

    private Integer id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;
}
