package com.huotu.huobanplus.sns.service;

import com.huotu.huobanplus.sns.model.AppCatalogModel;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */

public interface CategoryService {

    List<AppCatalogModel> getAppCatalogList(Integer id);
}
