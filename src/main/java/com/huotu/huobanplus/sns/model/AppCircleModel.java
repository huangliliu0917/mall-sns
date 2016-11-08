package com.huotu.huobanplus.sns.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/9/29.
 */
@Getter
@Setter
@EqualsAndHashCode
public class AppCircleModel {
    /**
     * 圈子ID
     */
    private Long circleId;
    /**
     * 名称
     */
    private String name;
    /**
     * 图片地址
     */
    private String pictureUrl;
    /**
     * 创建时间
     */
    private Long date;

    /**
     * 跳转地址
     */
    private String url;
}
