package com.huotu.huobanplus.sns.controller.admin;

import com.huotu.huobanplus.sns.model.admin.AdminCategoryModel;
import com.huotu.huobanplus.sns.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
@Controller
@RequestMapping("/top/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/categoryList")
    public String categoryList(Integer categoryType, Integer id, Model model) {
        List<AdminCategoryModel> adminCategoryModels = categoryService.getAdminCategoryList(categoryType, id);
        model.addAttribute("list", adminCategoryModels);
        return "admin/category/categoryList";
    }


}
