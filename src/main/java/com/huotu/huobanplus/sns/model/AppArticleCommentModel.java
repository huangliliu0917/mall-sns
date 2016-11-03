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

import java.io.Serializable;

/**
 * Created by jin on 2016/11/2.
 * 评论中的冗余评论
 */
@Getter
@Setter
public class AppArticleCommentModel implements Serializable {

    /**
     * 评论id
     */
    private Long pid;
    /**
     * 评论用户名称
     */
    private String userName;
    /**
     * 评论用户头像
     */
    private String userHeadUrl;
    /**
     * 点赞量
     */
    private Long clickAmount;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论日期
     */
    private Long date;

    /**
     * 楼层
     */
    private Long floor;
}
