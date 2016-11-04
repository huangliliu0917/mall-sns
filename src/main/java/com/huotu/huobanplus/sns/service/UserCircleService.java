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
import com.huotu.huobanplus.sns.entity.UserCircle;
import com.huotu.huobanplus.sns.exception.ConcernException;
import com.huotu.huobanplus.sns.exception.NeedLoginException;
import com.huotu.huobanplus.sns.model.AppCircleIndexArticleListModel;
import com.huotu.huobanplus.sns.model.AppCircleIndexListModel;

import java.io.IOException;
import java.util.List;

/**
 * 用户关注圈子的服务
 * Created by jin on 2016/10/10.
 */
public interface UserCircleService {

    /**
     * 关注圈子
     *
     * @param id 圈子id
     * @throws ConcernException 关注异常
     * @throws NeedLoginException     登录异常
     * @throws IOException      读写数据异常
     */
    void concern(Long id) throws ConcernException, NeedLoginException, IOException;

    /**
     * 取消关注圈子
     *
     * @param id 圈子id
     * @throws ConcernException 关注异常
     * @throws NeedLoginException     登录异常
     * @throws IOException      读写数据异常
     */
    void cancelConcern(Long id) throws ConcernException, NeedLoginException, IOException;

    /**
     * 根据商户ID，和用户ID和最后一条id获取用户关注圈子的列表，默认倒叙排序
     * @param customerId    商户ID
     * @param userId        用户ID
     * @param lastId        小于的id(为空则是取最新的)
     * @return              list
     * @throws IOException
     */
    List<UserCircle> getUserCircleList(Long customerId,Long userId,Long lastId) throws IOException;

    /**
     * 根据关注列表获取model
     * @param userCircles   关注列表
     * @return              model列表
     * @throws IOException
     */
    AppCircleIndexListModel[] getCircleIndexListModelList(List<UserCircle> userCircles) throws IOException;

    /**
     * 根据关注实体，获取关注model
     * @param userCircle    关注实体
     * @return
     * @throws IOException
     */
    AppCircleIndexListModel getCircleIndexListModel(UserCircle userCircle) throws IOException;

    /**
     * 根据文章获取model
     * @param article   文章实体
     * @return          model
     */
    AppCircleIndexArticleListModel getArticleModel(Article article);

    /**
     * 根据文章list,获取model列表
     * @param articles  文章list
     * @return          model列表
     */
    List<AppCircleIndexArticleListModel> getArticleModelList(List<Article> articles);
}
