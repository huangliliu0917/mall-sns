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
import com.huotu.huobanplus.sns.entity.Article;
import com.huotu.huobanplus.sns.entity.ArticleComment;
import com.huotu.huobanplus.sns.model.common.CommentStatus;
import com.huotu.huobanplus.sns.repository.ArticleCommentRepository;
import com.huotu.huobanplus.sns.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @Autowired
    private ArticleCommentRepository articleCommentRepository;

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
     * 设置文章是否禁用
     * @param id    文章ID
     * @return      无，只要正常就说明禁用成功
     * @throws Exception
     */
    @RequestMapping("/deleteArticle")
    @ResponseBody
    public ModelMap deleteArticle(@RequestParam(required = true) Long id,boolean enabled) throws Exception{
        ModelMap modelMap=new ModelMap();
        Article article=articleRepository.findOne(id);
        if(article!=null){
            article.setEnabled(enabled);
            articleRepository.save(article);
        }
        return modelMap;
    }

    /**
     * 获取某个商家某个文章的评论
     * @param customerId        商家ID
     * @param articleId         文章ID
     * @param pageNo            第几页(0:第一页)
     * @param pageSize          每页几条
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/viewComments",method = RequestMethod.GET)
    public String viewComments(@CustomerId Long customerId,@RequestParam(required = true) Long articleId,
                               Integer pageNo,Integer pageSize,Model model) throws Exception{
        if(pageNo==null||pageNo<0){
            pageNo=0;
        }
        if(pageSize==null){
            pageSize=20;
        }
        Page<ArticleComment> articleComments=articleCommentRepository.
                findByArticle_IdAndCustomerId(articleId,customerId,new PageRequest(pageNo,pageSize));

        model.addAttribute("list",articleComments.getContent());
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("articleId",articleId);
        return "/admin/circle/commentList";
    }


    /**
     * 修改评论状态
     * @param id 文章评论ID
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modifyCommentStatus",method = RequestMethod.GET)
    @ResponseBody
    public ModelMap ModifyCommentStatus(Long id) throws Exception{
        ArticleComment comment=articleCommentRepository.findOne(id);
        CommentStatus oldStatus=comment.getCommentStatus();
        switch (oldStatus){
            case Delete:
                comment.setCommentStatus(CommentStatus.Normal);
                break;
            case Normal:
                comment.setCommentStatus(CommentStatus.Delete);
                break;
            default:
                comment.setCommentStatus(CommentStatus.Normal);
        }
        articleCommentRepository.save(comment);

        //todo 调用服务
        return new ModelMap();
    }

}
