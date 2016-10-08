package com.huotu.huobanplus.sns.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/9/29.
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
     * 图片地址
     */
    private String pictureUrl;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户头像
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
