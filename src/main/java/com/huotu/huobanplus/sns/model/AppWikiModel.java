package com.huotu.huobanplus.sns.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/9/29.
 */
@Getter
@Setter
public class AppWikiModel {
    /**
     * 百科id
     */
    private Long pid;
    /**
     * 百科标题
     */
    private String name;
    /**
     * 百科内容
     */
    private String content;
    /**
     * 发布时间
     */
    private Long time;
    /**
     * 发布的用户
     */
    private String userName;
    /**
     * 发布的用户头像
     */
    private String userHeadUrl;

    /**
     * 专家
     */
    private AppExpertsModel experts;

    /**
     * 广告内容
     */
    private String adConent;
}
