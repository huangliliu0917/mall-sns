package com.huotu.huobanplus.sns.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 圈子文章详情model
 * Created by slt on 2016/9/29.
 */
@Getter
@Setter
public class AppCircleArticleDetailModel {
    /**
     * 文章id
     */
    private Long pid;
    /**
     * 文章标题
     */
    private String name;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 封面图片地址
     */
    private String pictureUrl;
    /**
     * 发布用户名称
     */
    private String userName;
    /**
     * 发布用户头像
     */
    private String userHeadUrl;
    /**
     * 文章发表时间
     */
    private Long time;
    /**
     * 点赞量
     */
    private Long clickAmount;
}
