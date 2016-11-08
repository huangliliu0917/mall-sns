package com.huotu.huobanplus.sns.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/9/29.
 */
@Getter
@Setter
@EqualsAndHashCode
public class AppCircleArticleModel {
    /**
     * 文章id
     */
    private Long pid;
    /**
     * 文章标题
     */
    private String name;
    /**
     * 图片地址
     */
    private String pictureUrl;
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userHeadUrl;

    /**
     * 是否已关注该文章的发布者
     */
    private Boolean concerned;
    /**
     * 用户等级
     */
    private Long userLevel;

    /**
     * 文章发表时间
     */
    private Long time;
    /**
     * 浏览量
     */
    private Long viewAmount;
    /**
     * 评论量
     */
    private Long commentsAmount;

}
