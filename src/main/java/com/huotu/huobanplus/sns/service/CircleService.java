/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service;

import com.huotu.huobanplus.sns.entity.Circle;
import com.huotu.huobanplus.sns.model.AppCircleIndexSuggestModel;
import com.huotu.huobanplus.sns.model.AppCircleIntroduceModel;
import com.huotu.huobanplus.sns.model.AppCircleModel;
import com.huotu.huobanplus.sns.model.admin.CircleListModel;
import com.huotu.huobanplus.sns.model.admin.CircleSearchModel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.IOException;
import java.util.List;

/**
 * 圈子逻辑层
 * Created by slt on 2016/10/12.
 */
public interface CircleService {

    /**
     * 获取圈子model列表
     *
     * @param circles 圈子实体列表
     * @return  圈子model列表
     */
    List<CircleListModel> findCircleListModel(List<Circle> circles);

    /**
     * 将圈子实体转换为圈子model
     *
     * @param circle    圈子实体
     * @return      圈子列表model
     */
    CircleListModel circleToCircleModel(Circle circle);

    /**
     * 将圈子转换为详情的圈子model(model数据不一样)
     * @param circle    圈子实体
     * @return          圈子列表model
     */
    CircleListModel circleToDetailsCircleModel(Circle circle);

    /**
     * 根据查询model查询圈子列表
     *
     * @param searchModel 查询model
     * @return 圈子实体列表
     * @throws IOException 数据库查询出错
     */
    Page<Circle> findCircleList(CircleSearchModel searchModel) throws IOException;

    /**
     * app获取圈子列表
     * @param lastId        最后一个记录的ID
     * @param customerId    商户ID
     * @return              圈子列表
     * @throws IOException
     */
    List<Circle> findAppCircleList(Long customerId,Long lastId) throws IOException;

    /**
     * 新增圈子
     * @param circleListModel 圈子model
     * @throws IOException
     */
    void addCircle(CircleListModel circleListModel) throws IOException;

    /**
     * 更新圈子
     * @param circleListModel   圈子model
     * @throws IOException
     */
    void updateCircle(CircleListModel circleListModel) throws IOException;

    /**
     * 根据圈子实体获取前台首页圈子的model
     * @param circles   圈子实体列表
     * @return          model列表
     */
    AppCircleIndexSuggestModel[] getCircleAppModels(List<Circle> circles);


    /**
     * 根据是否热门查询圈子列表
     *
     * @param suggested 是否热门
     * @return 圈子列表
     * @throws IOException
     */
    List<Circle> findBySuggested(Boolean suggested) throws IOException;

    /**
     * 根据圈子实体返回圈子详情model
     * @param circle    圈子实体
     * @return          详情model
     */
    AppCircleIntroduceModel getCircleDetailsModel(Circle circle);

    /**
     * 根据圈子实体获取app圈子model
     * @param circle       实体
     * @return              model
     */
    AppCircleModel getAppCircleModel(Circle circle);

    Page getDataList(JpaSpecificationExecutor executor);

}
