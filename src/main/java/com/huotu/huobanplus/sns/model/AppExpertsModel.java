package com.huotu.huobanplus.sns.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 专家
 * Created by Administrator on 2016/9/30.
 */
@Getter
@Setter
public class AppExpertsModel {
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userHeadUrl;
    /**
     * 简介
     */
    private String summary;
    /**
     * 专家跳转地址
     */
    private String url;
}
