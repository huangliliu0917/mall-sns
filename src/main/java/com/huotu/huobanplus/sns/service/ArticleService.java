/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service;

import com.huotu.huobanplus.sns.entity.Article;
import com.huotu.huobanplus.sns.entity.ArticleComment;
import com.huotu.huobanplus.sns.entity.User;
import com.huotu.huobanplus.sns.exception.ClickException;
import com.huotu.huobanplus.sns.model.AppCircleArticleModel;
import com.huotu.huobanplus.sns.model.AppWikiListModel;
import com.huotu.huobanplus.sns.model.AppWikiModel;
import com.huotu.huobanplus.sns.model.admin.AdminArticleEditModel;
import com.huotu.huobanplus.sns.model.admin.AdminArticlePageModel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
public interface ArticleService {
    List<AppWikiListModel> getAppWikiList(Integer catalogId, Long lastId);

    AppWikiModel getAppWiki(Long id);

    AdminArticlePageModel getAdminArticleList(Integer articleType, String name, Integer pageNo, Integer pageSize);

    AdminArticleEditModel getAdminArticle(String type, Integer articleType, Long id) throws URISyntaxException;


    /**
     * @param articleType
     * @param id
     * @param name
     * @param userId
     * @param pictureUrl
     * @param content
     * @param summary
     * @param categoryId
     * @param circleId
     * @param adConent
     */
    Article save(Integer articleType, Long id
            , String name, Long userId, String pictureUrl, String content
            , String summary, Integer categoryId, Long circleId, String adConent, String tags);

    /**
     * 新增一篇文章后，圈子文章数量加1，用户文章数加1，每个关注了该用户的都保存一份文章
     * 暂时保存在{@link com.huotu.huobanplus.sns.entity.UserArticle}中，后期考虑用redis
     *
     * @param articleType 文章类型
     * @param id          文章id
     * @param name        文章名字
     * @param user        作者
     * @param pictureUrl  文章图片
     * @param summary     简介
     * @param circleId    圈子id
     */
    void addArticleResult(Integer articleType, Long id
            , String name, User user, String pictureUrl
            , String summary, Long circleId) throws IOException, InterruptedException;

    /**
     * 评论文章
     *
     * @param id      文章id
     * @param content 评论内容
     * @param user    评论者
     * @throws IOException
     */
    ArticleComment commentArticle(Long id, String content, User user) throws IOException;

    /**
     * 获得用户文章列表
     *
     * @param userId
     * @param lastId
     * @return
     */
    List<AppCircleArticleModel> getUserArticleList(Long userId, Long lastId);

    /**
     * 给某篇文章点赞
     *
     * @param article 文章
     * @param user    点赞用户
     * @throws IOException
     * @throws ClickException 点赞异常
     */
    void articleClick(Article article, User user) throws IOException, ClickException;

    /**
     * @param articleComment 评论
     * @param user           评论用户
     * @param content        评论内容
     * @throws IOException
     */
    void replyComment(ArticleComment articleComment, User user, String content) throws IOException;
}
