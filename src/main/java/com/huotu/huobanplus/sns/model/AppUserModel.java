package com.huotu.huobanplus.sns.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/9/29.
 */
@Getter
@Setter
public class AppUserModel {
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userHeadUrl;
    /**
     * 排名
     */
    private Long rank;
    /**
     * 关注数
     */
    private Long userAmount;

    /**
     * 粉丝数
     */
    private Long fansAmount;
}
