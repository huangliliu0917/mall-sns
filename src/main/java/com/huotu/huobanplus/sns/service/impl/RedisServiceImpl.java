/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huotu.huobanplus.sns.entity.ArticleComment;
import com.huotu.huobanplus.sns.model.AppArticleCommentModel;
import com.huotu.huobanplus.sns.model.AppCircleArticleCommentsModel;
import com.huotu.huobanplus.sns.repository.ArticleCommentRepository;
import com.huotu.huobanplus.sns.service.RedisService;
import com.huotu.huobanplus.sns.utils.ContractHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/11/7.
 */
@Service
public class RedisServiceImpl implements RedisService {

    private static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisTemplate<String, AppArticleCommentModel> articleCommentRedisTemplate;
    @Autowired
    private RedisTemplate<String, AppCircleArticleCommentsModel> appCircleArticleCommentsModelRedisTemplate;
    @Autowired
    private ArticleCommentRepository articleCommentRepository;

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
    public void addArticleComment(Long articleId, ArticleComment articleComment) throws IOException {
//        BoundListOperations<String, AppArticleCommentModel> articleCommentBoundListOperations =
//                articleCommentRedisTemplate.boundListOps(ContractHelper.articleCommentFlag + articleId);
//        articleCommentBoundListOperations.leftPush(model);

        ListOperations<String, AppCircleArticleCommentsModel> circleArticleCommentsModelListOperations =
                appCircleArticleCommentsModelRedisTemplate.opsForList();
        if (appCircleArticleCommentsModelRedisTemplate.getExpire(ContractHelper.articleCommentFlag + articleId) == -2) {
            List<ArticleComment> list = articleCommentRepository.findByArticleId(articleId, new Sort(Sort.Direction.DESC, "id"));
            for (ArticleComment comment : list) {
                circleArticleCommentsModelListOperations.rightPush(ContractHelper.articleCommentFlag + articleId,
                        changeCircleModel(comment));
            }
            //设置过期时间
            appCircleArticleCommentsModelRedisTemplate.expire(ContractHelper.articleCommentFlag + articleId, 7, TimeUnit.DAYS);
        } else {
            circleArticleCommentsModelListOperations.leftPush(ContractHelper.articleCommentFlag + articleId,
                    changeCircleModel(articleComment));
        }
    }

    @Override
    public void addArticleCommentByCommentId(Long articleCommentId, List<AppArticleCommentModel> models) throws IOException {

        BoundListOperations<String, AppArticleCommentModel> ArticleReplyCommentBoundListOperations =
                articleCommentRedisTemplate.boundListOps(ContractHelper.articleReplyCommentFlag + articleCommentId);
        articleCommentRedisTemplate.expire(ContractHelper.articleReplyCommentFlag + articleCommentId, 1, TimeUnit.DAYS);
        for (int i = 0; i < models.size(); i++) {
            ArticleReplyCommentBoundListOperations.rightPush(models.get(i));
        }
    }

    @Override
    public AppArticleCommentModel changeModel(ArticleComment articleComment) {
        if (Objects.nonNull(articleComment)) {
            AppArticleCommentModel model = new AppArticleCommentModel();
            model.setContent(articleComment.getContent());
            model.setDate(articleComment.getDate().getTime());
            model.setFloor(articleComment.getFloor());
            model.setPid(articleComment.getId());
            model.setUserHeadUrl(articleComment.getUser().getImgURL());
            model.setUserName(articleComment.getUser().getNickName());
            return model;
        }
        return null;
    }

    public AppCircleArticleCommentsModel changeCircleModel(ArticleComment articleComment) {
        if (Objects.nonNull(articleComment)) {
            AppCircleArticleCommentsModel model = new AppCircleArticleCommentsModel();
            model.setContent(articleComment.getContent());
            model.setDate(articleComment.getDate().getTime());
            model.setFloor(articleComment.getFloor());
            model.setPid(articleComment.getId());
            model.setUserHeadUrl(articleComment.getUser().getImgURL());
            model.setUserName(articleComment.getUser().getNickName());
            return model;
        }
        return null;
    }

    @Override
    public List<AppCircleArticleCommentsModel> findByArticleId(Long articleId, Long lastId, Integer pageSize) throws IOException {
        ListOperations<String, AppCircleArticleCommentsModel> circleArticleCommentsModelListOperations =
                appCircleArticleCommentsModelRedisTemplate.opsForList();
        List<AppCircleArticleCommentsModel> list;
        if (null != lastId) {
            list = circleArticleCommentsModelListOperations
                    .range(ContractHelper.articleCommentFlag + articleId, 0L, -1);
            int index = 0;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (list.get(i).getPid().equals(lastId)) {
                    index = i;
                    break;
                }
            }
            if (null == pageSize) {
                list = list.subList(index + 1, size + 1);
            } else {
                list = list.subList(index + 1, pageSize + 1);
            }
        } else {
            if (appCircleArticleCommentsModelRedisTemplate.getExpire(ContractHelper.articleCommentFlag + articleId) > 0) {
                if (null == pageSize) {
                    list = circleArticleCommentsModelListOperations
                            .range(ContractHelper.articleCommentFlag + articleId, 0L, -1);

                } else {
                    list = circleArticleCommentsModelListOperations
                            .range(ContractHelper.articleCommentFlag + articleId, 0L, pageSize - 1);
                }
            } else {
                list = getArticleCommentListFromDatabase(articleId);
                if (null != pageSize) {
                    list = list.subList(0, pageSize);
                }
            }

        }
        return list;
    }

    public List<AppArticleCommentModel> getArticleCommentModelList(Long articleCommentId) throws IOException {
        String path = articleCommentRepository.findPathByArticleCommentId(articleCommentId);
        if (!StringUtils.isEmpty(path)) {
            //错误程序
//            List<AppArticleCommentModel> list = objectMapper.readValue(path,new TypeReference<List<AppArticleCommentModel>>() {});
//            return list;
        }
        return null;
    }

    @Override
    public List<AppCircleArticleCommentsModel> getArticleCommentListFromDatabase(Long articleId) throws IOException {
        ListOperations<String, AppCircleArticleCommentsModel> circleArticleCommentsModelListOperations =
                appCircleArticleCommentsModelRedisTemplate.opsForList();
        List<AppCircleArticleCommentsModel> models = new ArrayList<>();
        List<ArticleComment> list = articleCommentRepository.findByArticleId(articleId, new Sort(Sort.Direction.DESC, "id"));
        for (ArticleComment comment : list) {
            AppCircleArticleCommentsModel model = changeCircleModel(comment);
            circleArticleCommentsModelListOperations.rightPush(ContractHelper.articleCommentFlag + articleId,
                    model);
            models.add(model);
        }
        return models;
    }
}
