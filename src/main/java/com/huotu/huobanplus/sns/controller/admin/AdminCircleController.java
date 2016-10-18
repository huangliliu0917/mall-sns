/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.controller.admin;

import com.huotu.huobanplus.sns.entity.Circle;
import com.huotu.huobanplus.sns.model.admin.CircleListModel;
import com.huotu.huobanplus.sns.model.admin.CircleSearchModel;
import com.huotu.huobanplus.sns.repository.CircleRepository;
import com.huotu.huobanplus.sns.service.CircleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/10/11.
 */
@Controller
@RequestMapping("/top/circle")
public class AdminCircleController {
    @Autowired
    private CircleService circleService;

    @Autowired
    private CircleRepository circleRepository;


    /**
     * 返回圈子列表
     * @return
     */
    @RequestMapping(value = "/getCircleList",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap getCircleList(@RequestBody CircleSearchModel circleSearchModel) throws Exception{

        Page<Circle> circles=circleService.findCircleList(circleSearchModel);
        List<CircleListModel> models=circleService.findCircleListModel(circles.getContent());

        ModelMap modelMap=new ModelMap();
        modelMap.put("data",models);
        modelMap.put("total",circles.getTotalElements());
        modelMap.put("totalPage",circles.getTotalPages());
        return modelMap;

    }

    /**
     * 根据圈子id,打开编辑页面,如果id为空则是新建
     * @param id        圈子ID
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/editCircle",method = RequestMethod.GET)
    public String editCircle(Long id, Model model) throws Exception{

        Circle circle=null;
        if(id!=null){
            circle=circleRepository.findOne(id);
        }
        CircleListModel circleListModel=circleService.circleToDetailsCircleModel(circle);
        model.addAttribute("circleListModel",circleListModel);
        return "/admin/circle/modifyCircle";
    }

    /**
     * 获取一个圈子实体
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getCircle",method = RequestMethod.GET)
    @ResponseBody
    public ModelMap getCircle(Long id) throws Exception{
        ModelMap modelMap=new ModelMap();
        Circle circle=null;
        if(id!=null){
            circle=circleRepository.findOne(id);
        }

        CircleListModel circleListModel=circleService.circleToDetailsCircleModel(circle);



//        if(circle==null){
//            modelMap.addAttribute("status","500");
//        }else {
//            modelMap.addAttribute("status","200");
//            modelMap.addAttribute("circle",circle);
//        }
        return modelMap;
    }

    /**
     * 保存圈子
     * @param circleListModel
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "saveCircle",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap saveCircle(@RequestBody CircleListModel circleListModel) throws Exception{
        ModelMap modelMap=new ModelMap();
        if(circleListModel.getCircleId()==null){
            circleService.addCircle(circleListModel);
        }else {
            circleService.updateCircle(circleListModel);
        }
        return modelMap;
    }

    @RequestMapping(value = "/commandIndex", method = RequestMethod.GET)
    public String commandIndex(Model model) throws IOException {
        return "/admin/circle/circleIndex";
    }
}
