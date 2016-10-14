package com.huotu.huobanplus.sns.controller.admin;

import com.huotu.huobanplus.sns.model.admin.AdminTagsModel;
import com.huotu.huobanplus.sns.model.admin.AdminTagsPageModel;
import com.huotu.huobanplus.sns.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016/10/11.
 */
@Controller
@RequestMapping("/top/tags")
public class AdminTagsController {


    @Autowired
    private TagsService tagsService;

    @RequestMapping("/tagsList")
    public String tagsList() {

        return "admin/tags/tagsList";
    }

    @RequestMapping("list")
    @ResponseBody
    public AdminTagsPageModel list(String name, Integer pageNo, Integer pageSize) {
        return tagsService.getAdminTagsList(name, pageNo, pageSize);
    }


    @RequestMapping("/tagsEdit/{type}/{id}")
    public String tagsEdit(@PathVariable("type") String type
            , @PathVariable("id") Integer id, Model model) {
        if (type != null && type.equals("edit") && id != null && id > 0) {
            model.addAttribute("data", tagsService.getAdminTags(id));
        } else {
            AdminTagsModel adminTagsModel = new AdminTagsModel(0, "");
            model.addAttribute("data", adminTagsModel);
        }
        return "admin/tags/tagsEdit";
    }

    @RequestMapping("/tagsEdit.save")
    public String tagsEditSave(Integer id, String name) throws Exception {
        tagsService.save(id, name);
        return "redirect:/top/tags/tagsList";
    }
}
