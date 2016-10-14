package com.huotu.huobanplus.sns.model.admin;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */
@Getter
@Setter
public class AdminCategoryEditModel {
    private AdminCategoryModel data;
    private List<AdminBaseCategoryModel> categoryList;
}
