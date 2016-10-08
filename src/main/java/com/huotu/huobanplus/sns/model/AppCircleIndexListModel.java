package com.huotu.huobanplus.sns.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Administrator on 2016/9/29.
 */
@Getter
@Setter
public class AppCircleIndexListModel {

    private Long pid;
    /**
     * 圈子名称
     */
    private String name;
    /**
     * 图片地址
     */
    private String pictureUrl;
    /**
     * 关注人数
     */
    private String num;

    /**
     * 圈子介绍连接地址
     */
    private String introduceUrl;

    /**
     * 圈子连接地址
     */
    private String url;

    /**
     * 文章列表
     */
    List<AppCircleIndexArticleListModel> list;
}
