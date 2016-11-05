/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/9/29.
 */
@Getter
@Setter
public class AppCircleIndexArticleListModel {
    /**
     * 文章ID
     */
    private Long articleId;
    /**
     * 文章标题
     */
    private String name;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 浏览量
     */
    private Long viewAmount;
    /**
     * 评论数量
     */
    private Long commentsAmount;
}
