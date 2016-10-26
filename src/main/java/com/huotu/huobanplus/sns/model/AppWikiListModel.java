package com.huotu.huobanplus.sns.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/9/29.
 */
@Getter
@Setter
public class AppWikiListModel {

    /**
     * 百科id
     */
    private Long pid;

    /**
     * 标题
     */
    private String name;

    /**
     * 图片
     */
    private String pictureUrl;
    /**
     * 简介
     */
    private String summary;

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
     * 用户等级
     */
    private Long userLevel;

    /**
     * 关注过的
     */
    private Boolean concerned;
    /**
     * 文章发表时间
     */
    private Long time;
    /**
     * 浏览量
     */
    private Long viewAmount;
}
