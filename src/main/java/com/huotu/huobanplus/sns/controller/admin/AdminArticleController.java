/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.controller.admin;

import com.huotu.huobanplus.sns.entity.Article;
import com.huotu.huobanplus.sns.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 文章操作
 * Created by slt on 2016/10/11.
 */
@Controller
@RequestMapping("/top/article")
public class AdminArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    /**
     * 文章置顶取消操作
     * @param id    文章ID
     * @return      isTop：修改之后，该文章最新的置顶信息
     * @throws Exception
     */
    @RequestMapping("/setTop")
    @ResponseBody
    public ModelMap setTop(@RequestParam(required = true) Long id) throws Exception{
        ModelMap modelMap=new ModelMap();
        Article article=articleRepository.findOne(id);
        if(article!=null){
            Boolean isTop=article.getTop();
            if(isTop==null){
                article.setTop(true);
            }else {
                article.setTop(!isTop);
            }
            modelMap.addAttribute("isTop",article.getTop());
            articleRepository.save(article);
        }
        return modelMap;
    }


    /**
     * 删除文章
     * @param id    文章ID
     * @return      无，只要正常就说明删除成功
     * @throws Exception
     */
    @RequestMapping("/deleteArticle")
    @ResponseBody
    public ModelMap deleteArticle(@RequestParam(required = true) Long id) throws Exception{
        ModelMap modelMap=new ModelMap();
        Article article=articleRepository.findOne(id);
        if(article!=null){
            articleRepository.delete(article.getId());
        }
        return modelMap;
    }

}
