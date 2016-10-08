package com.huotu.huobanplus.sns.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 圈子介绍
 * Created by Administrator on 2016/9/29.
 */
@Getter
@Setter
public class AppCircleIntroduceModel {
    /**
     * 名称
     */
    private String name;
    /**
     * 图片地址
     */
    private String pictureUrl;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 组长
     */
    private String leaderName;
    /**
     * 组长头像地址
     */
    private String leaderHeadUrl;

    /**
     * 组长等级
     */
    private Integer leaderLevel;

    /**
     * 关注人数
     */
    private Long concermAmount;
    /**
     * 文章数量
     */
    private Long articleAmount;
    /**
     * 简介
     */
    private String summary;

    /**
     * 创建时间
     */
    private Long date;
}
