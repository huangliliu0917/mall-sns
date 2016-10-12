/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.controller.admin;

import com.huotu.huobanplus.sns.entity.User;
import com.huotu.huobanplus.sns.service.UserService;
import com.huotu.huobanplus.sns.utils.ContractHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by Administrator on 2016/10/11.
 */
@SuppressWarnings("unused")
@Controller
@RequestMapping("/top/user")
public class AdminUserController {

    @Autowired
    private UserService userService;

//    private final static String lessThan = "<";

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() throws IOException {
//        model.addAttribute("lessThan",lessThan);
        return "/admin/user/userList";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap list(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize,
                         @RequestParam(required = false) String nickName,
                         @RequestParam(required = false) Long authenticationId,
                         @RequestParam(required = false) Long levelId, String sortName, String sortType) throws IOException {
        if (Objects.isNull(page)) page = ContractHelper.list_page;
        if (Objects.isNull(pageSize)) pageSize = ContractHelper.list_pageSize;
        Sort sort;
        if (sortType.equals("asc"))
            sort = new Sort(Sort.Direction.ASC, sortName);
        else
            sort = new Sort(Sort.Direction.DESC, sortName);
        Pageable pageable = new PageRequest(page - 1, pageSize, sort);
        Page<User> pages = userService.findByNickNameAndAuthenticationIdAndLevelId(nickName, authenticationId,
                levelId, pageable);
        Long count = pages.getTotalElements();
        int pageCount = Integer.parseInt(count.toString()) / pageSize + 1;
        ModelMap map = new ModelMap();
        map.addAttribute("list", pages.getContent());
        map.addAttribute("total", pages.getTotalElements());
        map.addAttribute("pageSize", pageSize);
        map.addAttribute("page", page);
        map.addAttribute("pageCount", pageCount);
        return map;
    }
}
