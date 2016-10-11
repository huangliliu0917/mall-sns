/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.controller.admin;

import com.huotu.huobanplus.sns.entity.Level;
import com.huotu.huobanplus.sns.repository.LevelRepository;
import com.huotu.huobanplus.sns.utils.ContractHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by jin on 2016/10/11.
 */
@Controller
@RequestMapping(value = "/top/level")
public class AdminLevelController {

    @Autowired
    private LevelRepository levelRepository;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(@RequestParam(required = false) Integer page,
                        @RequestParam(required = false) Integer pageSize, Model model) throws IOException {
        if (Objects.isNull(page)) page = ContractHelper.list_page;
        if (Objects.isNull(pageSize)) pageSize = ContractHelper.list_pageSize;
        Sort sort = new Sort(Sort.Direction.ASC, "experience");
        Pageable pageable = new PageRequest(page - 1, pageSize, sort);
        Page<Level> pages = levelRepository.findAll(pageable);
        Long count = pages.getTotalElements();
        int pageCount = Integer.parseInt(count.toString()) / pageSize + 1;
        model.addAttribute("total", pages.getTotalElements());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("page", page);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("list", pages.getContent());
        return "";
    }
}
