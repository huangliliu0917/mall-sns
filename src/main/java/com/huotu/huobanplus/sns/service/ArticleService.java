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
import com.huotu.huobanplus.sns.entity.Concern;
import com.huotu.huobanplus.sns.entity.User;
import com.huotu.huobanplus.sns.exception.ClickException;
import com.huotu.huobanplus.sns.model.*;
import com.huotu.huobanplus.sns.model.admin.AdminArticleEditModel;
import com.huotu.huobanplus.sns.model.admin.AdminArticlePageModel;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2016/10/12.
 */
public interface ArticleService {
    List<AppWikiListModel> getAppWikiList(Long customerId, Integer catalogId, Long lastId);

    AppWikiModel getAppWiki(Long id);

    AdminArticlePageModel getAdminArticleList(Long customerId, Integer articleType, String name, Integer pageNo, Integer pageSize);

    AdminArticleEditModel getAdminArticle(String type, Integer articleType, Long id) throws URISyntaxException;


    /**
     * @param customerId
     * @param articleType
     * @param id          文章Id
     * @param name
     * @param userId
     * @param pictureUrl
     * @param content
     * @param summary
     * @param categoryId
     * @param circleId
     * @param adConent
     */
    Article save(Long customerId, Integer articleType, Long id
            , String name, Long userId, String pictureUrl, String content
            , String summary, Integer categoryId, Long circleId, String adConent, String tags);

    /**
     * 新增一篇文章后，圈子文章数量加1，用户文章数加1，每个关注了该用户的都保存一份文章
     * 暂时保存在{@link com.huotu.huobanplus.sns.entity.UserArticle}中，后期考虑用redis
     *
     * @param articleType 文章类型
     * @param name        文章名字
     * @param user        作者
     * @param pictureUrl  文章图片
     * @param content     简介
     * @param circleId    圈子id
     */
    Long addArticleResult(Integer articleType
            , String name, User user, String pictureUrl
            , String content, Long circleId) throws IOException, InterruptedException;

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
    @Transactional
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
     * 回复评论
     *
     * @param articleComment 评论
     * @param user           评论用户
     * @param content        评论内容
     * @throws IOException
     */
    ArticleComment replyComment(ArticleComment articleComment, User user, String content) throws IOException;

    /**
     * 文章搜索
     * @param customerId
     * @param key
     * @param lastId
     * @return
     */
    List<AppCircleIndexArticleListModel> search(Long customerId, String key, Long lastId);

    /**
     * 根据圈子ID，获取该圈子下置顶的文章
     * @param circleId      圈子ID
     * @param userId        当前用户ID
     * @param customerId    商户ID
     * @return
     * @throws IOException
     */
    AppCircleArticleModel[] getTopArticleModels(Long userId,Long customerId,Long circleId) throws IOException;

    /**
     * 获取某个圈子下的文章列表
     * @param customerId 商户ID
     * @param userId    用户ID
     * @param lastId    最后一条文章ID
     * @param circleId  圈子ID
     * @param type      排序类型
     * @return
     * @throws IOException
     */
    AppCircleArticleModel[] getArticleListModels(Long customerId,Long userId,Long lastId,Long circleId,Integer type) throws IOException;

    /**
     * 根据文章获取model
     * @param article   文章实体
     * @param toUserIds 关注的用户列表
     * @return          文章model
     */
    AppCircleArticleModel getAppCircleArticleModel(Article article,Set<Long> toUserIds);

    /**
     * 根据文章实体列表，获取发布用户列表
     * @param articles      文章列表
     * @return
     */
    Set<Long> getToUserIdsByArticles(List<Article> articles);

    /**
     * 根据关注列表，获取关注某人的用户列表
     * @param concerns      关注列表
     * @return
     */
    Set<Long> getToUserIdsByConcerns(List<Concern> concerns);

    /**
     * 根据文章(帖子)实体，转换为model
     * @param article   实体
     * @return          model
     */
    AppCircleArticleDetailModel getArticleDetailModel(Article article);


    /**
     * 从给定的文章列表中获取当前商家下的用户的关注其他用户的列表
     * @param articles      文章列表
     * @param userId        当前用户ID
     * @param customerId    商户ID
     * @return              关注用户的列表
     * @throws IOException
     */
    Set<Long> findAttentionUsersByArticles(List<Article> articles,Long userId,Long customerId) throws IOException;


}
