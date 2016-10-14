package com.huotu.huobanplus.sns.model.admin;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Administrator on 2016/10/13.
 */
@Getter
@Setter
public class AdminCategoryPageModel extends AdminBasePageModel {
    private List<AdminCategoryModel> list;
}
