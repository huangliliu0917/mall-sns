package com.huotu.huobanplus.sns.controller.admin;

import com.huotu.huobanplus.sns.entity.Slide;
import com.huotu.huobanplus.sns.repository.SlideRepository;
import com.huotu.huobanplus.sns.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by slt on 2016/10/17.
 */
@Controller
@RequestMapping("/top/slide")
public class AdminSlideController {
    @Autowired
    private SlideRepository slideRepository;

    @Autowired
    private SlideService slideService;

    /**
     * 获取banner数据并返回后台页面
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/getSlideList")
    public String getSlideList(Model model) throws Exception{
        List<Slide> slides=slideRepository.findAll();
        model.addAttribute("slides",slides);
        return "/admin/circle/bannerArticle";
    }

    /**
     * 保存banner文章，包括新增和修改
     * @param slide
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveSlide",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap saveSlide(@RequestBody Slide slide) throws Exception{
        ModelMap modelMap=new ModelMap();
        slide=slideRepository.save(slide);
        modelMap.addAttribute("slide",slide);
        return modelMap;
    }

    /**
     * 删除banner文章
     * @param slideId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteSlide",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap deleteSlide(Long slideId) throws Exception{
        ModelMap modelMap=new ModelMap();
        slideRepository.delete(slideId);
        return modelMap;
    }



}
