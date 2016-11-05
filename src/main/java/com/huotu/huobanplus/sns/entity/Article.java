/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * 文章
 * Created by Administrator on 2016/9/28.
 */
@Entity
@Cacheable(value = false)
@Getter
@Setter
public class Article extends AbstractArticle {

    /**
     * 所属圈子
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private Circle circle;

    /**
     * 所属分类(百科)
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private Category category;

    /**
     * 发布用户
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private User publisher;

    /**
     * 文章内容
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;


    /**
     * 广告内容
     */
    @Column(length = 500)
    private String adConent;

    /**
     * 点赞量
     */
    private long click;

    /**
     * 浏览量
     */
    private long view;

    /***
     * 回复评论量
     */
    private long comments;

    /**
     * 是否置顶
     */
    private Boolean top;

    /**
     * 文章标签
     */
    @OneToMany(cascade = {CascadeType.REFRESH})
    private Set<Tag> tags;

    /**
     * 是否可用(前端看不到禁用文章)
     */
    private Boolean enabled=true;

}
