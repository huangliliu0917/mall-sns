package com.huotu.huobanplus.sns.service;

import com.huotu.huobanplus.sns.model.AppCategoryModel;
import com.huotu.huobanplus.sns.model.admin.AdminCategoryModel;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */

public interface CategoryService {

    List<AppCategoryModel> getAppWikiCatalogList(Integer parentId);

    List<AdminCategoryModel> getAdminCategoryList(Integer categoryType, Integer parentId);
}
