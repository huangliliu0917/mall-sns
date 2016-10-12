package com.huotu.huobanplus.sns.service.impl;

import com.huotu.huobanplus.sns.entity.Category;
import com.huotu.huobanplus.sns.model.AppCategoryModel;
import com.huotu.huobanplus.sns.model.admin.AdminCategoryModel;
import com.huotu.huobanplus.sns.model.common.CategoryType;
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

    public List<AppCategoryModel> getAppWikiCatalogList(Integer parentId) {
        return toAppCategory(getCategoryList(parentId, CategoryType.Wiki));
    }


    /**
     * 获取分类列表
     *
     * @param parentId     父级id
     * @param categoryType 分类类型
     * @return
     */
    private List<Category> getCategoryList(Integer parentId, CategoryType categoryType) {
        Category category = null;
        if (parentId != null && parentId > 0) {
            category = categoryRepository.findOne(parentId);
        }
        return categoryRepository.findByParentAndCategoryType(category, categoryType);
    }

    private List<AppCategoryModel> toAppCategory(List<Category> categories) {
        List<AppCategoryModel> appCategoryModels = new ArrayList<>();
        categories.forEach(x -> {
            AppCategoryModel appCategoryModel = new AppCategoryModel();
            appCategoryModel.setPid(x.getId());
            appCategoryModel.setName(x.getName());
            appCategoryModels.add(appCategoryModel);
        });
        return appCategoryModels;
    }


    public List<AdminCategoryModel> getAdminCategoryList(Integer categoryType, Integer parentId) {
        List<Category> categories = getCategoryList(parentId, categoryType.equals(1) ? CategoryType.Wiki : CategoryType.Normal);
        return toAdminCategory(categories);
    }

    private List<AdminCategoryModel> toAdminCategory(List<Category> categories) {
        List<AdminCategoryModel> adminCategoryModels = new ArrayList<>();
        categories.forEach(x -> {
            AdminCategoryModel adminCategoryModel = new AdminCategoryModel();
            adminCategoryModel.setId(x.getId());
            adminCategoryModel.setName(x.getName());
            adminCategoryModel.setSort(x.getSort());
            adminCategoryModels.add(adminCategoryModel);
        });
        return adminCategoryModels;
    }
}
