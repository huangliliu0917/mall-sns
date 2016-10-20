package com.huotu.huobanplus.sns.controller.admin;


import com.huotu.huobanplus.sns.model.admin.AdminCategoryEditModel;
import com.huotu.huobanplus.sns.model.admin.AdminCategoryModel;
import com.huotu.huobanplus.sns.model.admin.AdminCategoryPageModel;
import com.huotu.huobanplus.sns.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by Administrator on 2016/10/12.
 */
@Controller
@RequestMapping("/top/category")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/categoryList/{categoryType}")
    public String categoryList(@PathVariable("categoryType") Integer categoryType, Model model) {
        model.addAttribute("categoryType", categoryType);
        return "admin/category/categoryList";
    }

    @RequestMapping("/list")
    @ResponseBody
    public AdminCategoryPageModel list(Integer categoryType, String name, Integer pageNo, Integer pageSize) {
        return categoryService.getAdminCategoryList(categoryType, name, pageNo, pageSize);
    }

    @RequestMapping("/categoryEdit/{categoryType}/{id}")
    public String categoryEdit(@PathVariable("categoryType") Integer categoryType
            , @PathVariable("id") Integer id, String extend, Model model) {
        if (id != null && id > 0) {
            AdminCategoryEditModel adminCategoryEditModel = new AdminCategoryEditModel();
            adminCategoryEditModel.setData(categoryService.getAdminCategory(id));
            adminCategoryEditModel.setCategoryList(categoryService.getAdminParentCategory(categoryType));
            model.addAttribute("data", adminCategoryEditModel);
        } else {
            AdminCategoryModel adminCategoryModel = new AdminCategoryModel();
            adminCategoryModel.setCategoryType(categoryType);
            adminCategoryModel.setId(0);
            adminCategoryModel.setSort(0);
            adminCategoryModel.setName("");
            adminCategoryModel.setParentName("");

            AdminCategoryEditModel adminCategoryEditModel = new AdminCategoryEditModel();
            adminCategoryEditModel.setData(adminCategoryModel);
            adminCategoryEditModel.setCategoryList(categoryService.getAdminParentCategory(categoryType));
            model.addAttribute("data", adminCategoryEditModel);
        }
        model.addAttribute("extend", extend);
        return "admin/category/categoryEdit";
    }

    @RequestMapping("/categoryEdit.save")
    public String categoryEditSave(Integer categoryType, Integer id, String name, Integer parent, Integer sort, String extend) throws Exception {
        if (id != null && parent != null && id > 0 && id.equals(parent)) {
            throw new Exception("父级不能设置为自己");
        }

        categoryService.save(categoryType, id, name, parent, sort);
        return "redirect:/top/category/categoryList/" + categoryType + "?extend=" + extend;
    }
}
