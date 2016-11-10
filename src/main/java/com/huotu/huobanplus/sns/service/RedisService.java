/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service;

import com.huotu.huobanplus.sns.entity.ArticleComment;
import com.huotu.huobanplus.sns.model.AppArticleCommentModel;
import com.huotu.huobanplus.sns.model.AppCircleArticleCommentsModel;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */
public interface RedisService {

    /**
     * 增加文章回复数
     *
     * @param articleId 文章id
     * @throws IOException
     */
    void addArticleCommentNum(Long articleId) throws IOException;

    /**
     * 新增文章评论
     *
     * @param articleId      文章id
     * @param articleComment 回复评论model
     * @throws IOException
     */
    void addArticleComment(Long articleId, ArticleComment articleComment) throws IOException;

    /**
     * 新增回复评论的完整冗余列表
     *
     * @param articleCommentId
     * @param models
     * @throws IOException
     */
    void addArticleCommentByCommentId(Long articleCommentId, List<AppArticleCommentModel> models) throws IOException;


    AppArticleCommentModel changeModel(ArticleComment articleComment);

    AppCircleArticleCommentsModel changeCircleModel(ArticleComment articleComment);

    /**
     * 查询某篇文章的评论
     *
     * @param articleId 文章id
     * @param lastId    最后的评论id，方便分页
     * @param pageSize  每页几条数据，如果为null，则查询全部
     * @return
     * @throws IOException
     */
    List<AppCircleArticleCommentsModel> findByArticleId(Long articleId, Long lastId, Integer pageSize) throws IOException;

    /**
     * 从数据库取出回复列表，并存入redis
     *
     * @param articleId 文章id
     * @return
     * @throws IOException
     */
    List<AppCircleArticleCommentsModel> getArticleCommentListFromDatabase(Long articleId) throws IOException;

    /**
     * 递归得到某条回复的上级回复，直到查找到root回复,并存入redis
     *
     * @param articleCommentId 回复id
     * @return
     * @throws IOException
     */
    List<AppArticleCommentModel> getArticleCommentModelList(Long articleCommentId) throws IOException;

}
