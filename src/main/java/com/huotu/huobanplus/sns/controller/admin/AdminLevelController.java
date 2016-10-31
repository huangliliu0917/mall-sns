/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.controller.admin;

import com.huotu.huobanplus.sns.annotation.CustomerId;
import com.huotu.huobanplus.sns.entity.Level;
import com.huotu.huobanplus.sns.repository.LevelRepository;
import com.huotu.huobanplus.sns.repository.UserRepository;
import com.huotu.huobanplus.sns.service.CommonConfigService;
import com.huotu.huobanplus.sns.utils.ContractHelper;
import com.huotu.huobanplus.sns.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Created by jin on 2016/10/11.
 */
@Controller
@RequestMapping(value = "/top/level")
public class AdminLevelController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private CommonConfigService commonConfigService;

    /**
     * 等级列表页
     *
     * @param page     页码
     * @param pageSize 每页条数
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(@CustomerId Long customerId, @RequestParam(required = false) Integer page,
                        @RequestParam(required = false) Integer pageSize, Model model) throws IOException {
        if (Objects.isNull(page)) page = ContractHelper.list_page;
        if (Objects.isNull(pageSize)) pageSize = ContractHelper.list_pageSize;
        Sort sort = new Sort(Sort.Direction.ASC, "experience");
        Pageable pageable = new PageRequest(page - 1, pageSize, sort);
        Page<Level> pages = levelRepository.findAllByCustomerId(customerId, pageable);
        model.addAttribute("total", pages.getTotalElements());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("page", page);
        model.addAttribute("pageCount", pages.getTotalPages());
        model.addAttribute("list", pages.getContent());
        model.addAttribute("url", commonConfigService.getWebUrl() + "/top/level/index?page=");
        return "/admin/user/levelList";
    }

    /**
     * 保存等级
     *
     * @param id         等级id
     * @param name       等级名字
     * @param experience 升级所需经验
     * @return
     * @throws IOException
     */
    @Transactional
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap save(
            @CustomerId Long customerId,
            @RequestParam(required = false) Long id, @RequestParam String name, @RequestParam Long experience
    ) throws IOException {
        Level level;
        if (Objects.isNull(id)) level = new Level();
        else level = levelRepository.findOne(id);
        level.setName(name);
        level.setExperience(experience);
        level.setCustomerId(customerId);
        levelRepository.save(level);
        ModelMap map = new ModelMap();
        map.addAttribute("success", true);
        return map;
    }

    /**
     * 删除等级
     *
     * @param id 等级id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ModelMap delete(@RequestParam Long id) throws IOException {
        Long count = userRepository.countByLevelId(id);
        if (count.intValue() > 0) {
            return ResultUtil.failure("该等级已有用户，无法删除");
        }
        levelRepository.delete(id);
        return ResultUtil.success();
    }

    /**
     * 根据主键得到等级详情
     *
     * @param id 等级id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getOne(@RequestParam Long id) throws IOException {
        Level level = levelRepository.getOne(id);
        return ResultUtil.success(null, level);
    }

    /**
     * 得到所有等级
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public List<Level> findAll(@CustomerId Long customerId) throws IOException {
        List<Level> levels = levelRepository.findAll(new Sort(Sort.Direction.ASC, "experience"));
        return levels;
    }
}
