package com.huotu.huobanplus.sns.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/9/29.
 */
@Getter
@Setter
public class AppCircleArticleCommentsModel {
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
     * 扩展信息 (评论的评论)
     */
    private String extend;
}
