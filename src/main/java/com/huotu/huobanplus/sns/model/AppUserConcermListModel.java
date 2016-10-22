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
 * 关注（被关注）的用户列表
 * Created by Administrator on 2016/9/29.
 */

@Getter
@Setter
public class AppUserConcermListModel {
    /**
     * 关注id
     */
    private Long pid;
    /**
     * 关注（被关注）的用户
     */
    private String userName;
    /**
     * 关注（被关注）的用户头像
     */
    private String userHeadUrl;
    /**
     * 关注（被关注）的用户等级
     */
    private String userLevelName;

    /**
     * 关注（被关注）的粉丝数
     */
    private Long fansAmount;

    /**
     * 关注（被关注）的文章数
     */
    private Long articleAmount;
}
