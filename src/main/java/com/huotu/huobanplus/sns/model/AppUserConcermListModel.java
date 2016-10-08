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
    private Long userAmount;

    /**
     * 关注（被关注）的文章数
     */
    private Long articleAmount;
}
