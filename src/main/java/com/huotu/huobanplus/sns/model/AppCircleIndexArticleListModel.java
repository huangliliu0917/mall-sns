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
    private Long id;
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
