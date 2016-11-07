/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service.impl;

import com.huotu.huobanplus.sns.model.AppArticleCommentModel;
import com.huotu.huobanplus.sns.service.RedisService;
import com.huotu.huobanplus.sns.utils.ContractHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2016/11/7.
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisTemplate<String, AppArticleCommentModel> articleCommentRedisTemplate;

    @Override
    public void addArticleCommentNum(Long articleId) throws IOException {
        BoundHashOperations<String, String, Long> articleOperations = redisTemplate
                .boundHashOps(ContractHelper.articleFlag + articleId);
        Long comments = articleOperations.get("comments");
        if (Objects.isNull(comments))
            articleOperations.put("comments", 1L);
        else
            articleOperations.put("comments", comments + 1L);
    }

    @Override
    public void addArticleComment(Long articleId, AppArticleCommentModel model) throws IOException {
        BoundListOperations<String, AppArticleCommentModel> articleCommentBoundListOperations =
                articleCommentRedisTemplate.boundListOps(ContractHelper.articleCommentFlag + articleId);
        articleCommentBoundListOperations.leftPush(model);
    }

    @Override
    public void addArticleCommentByCommentId(Long articleCommentId, List<AppArticleCommentModel> models) throws IOException {
        BoundListOperations<String, AppArticleCommentModel> ArticleReplyCommentBoundListOperations =
                articleCommentRedisTemplate.boundListOps(ContractHelper.articleReplyCommentFlag + articleCommentId);
        for (int i = 0; i < models.size(); i++) {
            ArticleReplyCommentBoundListOperations.rightPush(models.get(i));
        }
    }
}
