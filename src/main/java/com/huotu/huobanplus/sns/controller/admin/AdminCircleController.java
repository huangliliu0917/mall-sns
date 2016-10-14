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
        if(circle==null){
            circle=new Circle();
        }
        model.addAttribute("circle",circle);
        return "/admin/circle/modifyCircle";
    }

    /**
     * 保存圈子
     * @param circle
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "saveCircle",method = RequestMethod.POST)
    public String saveCircle(Circle circle) throws Exception{
        return "/admin/circle/circleList";
    }


}
