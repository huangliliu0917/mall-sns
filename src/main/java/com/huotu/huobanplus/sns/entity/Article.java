package com.huotu.huobanplus.sns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    private Long click;

    /**
     * 浏览量
     */
    private Long view;

    /***
     * 回复评论量
     */
    private Long comments;

    /**
     * 是否置顶
     */
    private Boolean top;

    /**
     * 文章标签
     */
    @OneToMany(cascade = {CascadeType.REFRESH})
    private List<Tag> tags;

}
