package com.huotu.huobanplus.sns.model;

import lombok.*;

/**
 * Created by Administrator on 2016/9/29.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AppCircleIndexSuggestModel {
    /**
     * 标题
     */
    private String title;
    /**
     * 图片地址
     */
    private String pictureUrl;
    /**
     * 跳转地址
     */
    private String url;
    /**
     * 人数
     */
    private Long num;
}
