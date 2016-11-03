package com.huotu.huobanplus.sns.controller.admin;

import com.huotu.huobanplus.sns.annotation.CustomerId;
import com.huotu.huobanplus.sns.boot.PublicParameterHolder;
import com.huotu.huobanplus.sns.model.admin.AdminTagsModel;
import com.huotu.huobanplus.sns.model.admin.AdminTagsPageModel;
import com.huotu.huobanplus.sns.model.common.TagsType;
import com.huotu.huobanplus.sns.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    public AdminTagsPageModel list(@CustomerId Long customerId, String name, Integer pageNo, Integer pageSize) {
        return tagsService.getAdminTagsList(customerId, name, pageNo, pageSize);
    }


    @RequestMapping("/tagsEdit/{id}")
    public String tagsEdit(@PathVariable("id") Integer id, String extend, Model model) {
        if (id != null && id > 0) {
            model.addAttribute("data", tagsService.getAdminTags(id));
        } else {
            AdminTagsModel adminTagsModel = new AdminTagsModel(0, "");
            model.addAttribute("data", adminTagsModel);
        }
        model.addAttribute("extend", extend);
        return "admin/tags/tagsEdit";
    }

    @RequestMapping("/tagsEdit.save")
    public String tagsEditSave(@CustomerId Long customerId, Integer id, String name, String extend) throws Exception {
        tagsService.save(customerId, id, name);
        return "redirect:/top/tags/tagsList?extend=" + extend;
    }


    @RequestMapping("/addTags")
    @ResponseBody
    public List<AdminTagsModel> addTags(@CustomerId Long customerId, Integer tagsType, Long id, String tagsIds) {
        return tagsService.addTags(customerId, tagsType, id, tagsIds);
    }

    @RequestMapping("/deleteTags")
    @ResponseBody
    public Boolean deleteTags(Integer tagsType, Long id, Integer tagsId) {
        tagsService.deleteRags(tagsType, id, tagsId);
        return true;
    }
}
