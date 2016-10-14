package com.huotu.huobanplus.sns.model.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Administrator on 2016/10/13.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminBaseCategoryModel {
    private Integer id;

    /**
     * 分类名称
     */
    private String name;
}
