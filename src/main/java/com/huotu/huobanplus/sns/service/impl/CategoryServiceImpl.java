package com.huotu.huobanplus.sns.service.impl;

import com.huotu.huobanplus.sns.entity.Category;
import com.huotu.huobanplus.sns.model.AppCategoryModel;
import com.huotu.huobanplus.sns.model.admin.AdminBaseCategoryModel;
import com.huotu.huobanplus.sns.model.admin.AdminCategoryModel;
import com.huotu.huobanplus.sns.model.admin.AdminCategoryPageModel;
import com.huotu.huobanplus.sns.model.admin.PagingModel;
import com.huotu.huobanplus.sns.model.common.CategoryType;
import com.huotu.huobanplus.sns.repository.CategoryRepository;
import com.huotu.huobanplus.sns.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private static Integer PAGE_SIZE = 10;

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


    public AdminCategoryPageModel getAdminCategoryList(Integer categoryType, String name, Integer pageNo, Integer pageSize) {
        AdminCategoryPageModel adminCategoryPageModel = new AdminCategoryPageModel();

        Pageable pageable = new PageRequest(pageNo - 1, pageSize, new Sort(Sort.Direction.ASC, "sort"));

//        Category category = null;
//        if (parentId != null && parentId > 0) {
//            category = categoryRepository.findOne(parentId);
//        }
        Page<Category> categories = null;
        if (!StringUtils.isEmpty(name)) {
            categories = categoryRepository.findByCategoryTypeAndNameLike(categoryType.equals(1) ? CategoryType.Wiki : CategoryType.Normal, name, pageable);
        } else {
            categories = categoryRepository.findByCategoryType(categoryType.equals(1) ? CategoryType.Wiki : CategoryType.Normal, pageable);
        }

        adminCategoryPageModel.setPage(new PagingModel(pageNo, pageSize, categories.getTotalPages(), categories.getTotalElements()));
        adminCategoryPageModel.setList(toAdminCategory(categories.getContent()));

        return adminCategoryPageModel;
    }

    public AdminCategoryModel getAdminCategory(Integer id) {
        Category category = categoryRepository.findOne(id);
        return toAdminCategory(category);
    }


    private List<AdminCategoryModel> toAdminCategory(List<Category> categories) {
        List<AdminCategoryModel> adminCategoryModels = new ArrayList<>();
        categories.forEach(x -> {
            adminCategoryModels.add(toAdminCategory(x));
        });
        return adminCategoryModels;
    }

    private AdminCategoryModel toAdminCategory(Category category) {
        if (category != null) {
            AdminCategoryModel adminCategoryModel = new AdminCategoryModel();
            adminCategoryModel.setId(category.getId());
            adminCategoryModel.setName(category.getName());
            adminCategoryModel.setSort(category.getSort());
            if (category.getParent() != null) {
                adminCategoryModel.setParentId(category.getParent().getId());
                adminCategoryModel.setParentName(category.getParent().getName());
            }
            adminCategoryModel.setCategoryType(category.getCategoryType().equals(CategoryType.Wiki) ? 1 : 0);
            return adminCategoryModel;
        }
        return null;
    }

    public void save(Integer categoryType, Integer id, String name, Integer parent, Integer sort) {
        Category category = null;
        if (id != null && id > 0)
            category = categoryRepository.findOne(id);
        else {
            category = new Category();
        }

        if (parent != null) {
            Category category1 = categoryRepository.findOne(parent);
            category.setParent(category1);
        }

        category.setName(name);
        category.setSort(sort);
        category.setCategoryType(categoryType.equals(1) ? CategoryType.Wiki : CategoryType.Normal);
        categoryRepository.save(category);
    }


    public List<AdminBaseCategoryModel> getAdminParentCategory(Integer categoryType) {
        List<AdminBaseCategoryModel> adminBaseCategoryModels = new ArrayList<>();
        List<Category> categories = categoryRepository.findByParentAndCategoryType(null, categoryType.equals(1) ? CategoryType.Wiki : CategoryType.Normal);
        categories.forEach(x -> {
            adminBaseCategoryModels.add(new AdminBaseCategoryModel(x.getId(), x.getName()));
        });
        return adminBaseCategoryModels;
    }

}
