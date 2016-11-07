/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service;

import com.huotu.huobanplus.sns.model.AppArticleCommentModel;

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
     * @param articleId 文章id
     * @param model     回复评论model
     * @throws IOException
     */
    void addArticleComment(Long articleId, AppArticleCommentModel model) throws IOException;

    /**
     * 新增回复评论的完整冗余列表
     *
     * @param articleCommentId
     * @param models
     * @throws IOException
     */
    void addArticleCommentByCommentId(Long articleCommentId, List<AppArticleCommentModel> models) throws IOException;
}
