package com.huotu.huobanplus.sns.service.impl;

import com.huotu.huobanplus.sns.entity.Category;
import com.huotu.huobanplus.sns.model.AppCatalogModel;
import com.huotu.huobanplus.sns.repository.CategoryRepository;
import com.huotu.huobanplus.sns.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<AppCatalogModel> getAppCatalogList(Integer id) {

        Category category = null;
        if (id != null && id > 0) {
            category = categoryRepository.findOne(id);
        }

        List<Category> categories = categoryRepository.findByParent(category);
        return toAppCatalog(categories);
    }

    private List<AppCatalogModel> toAppCatalog(List<Category> categories) {
        List<AppCatalogModel> appCatalogModels = new ArrayList<>();
        categories.forEach(x -> {
            AppCatalogModel appCatalogModel = new AppCatalogModel();
            appCatalogModel.setPid(x.getId());
            appCatalogModel.setName(x.getName());
            appCatalogModels.add(appCatalogModel);
        });
        return appCatalogModels;
    }
}
