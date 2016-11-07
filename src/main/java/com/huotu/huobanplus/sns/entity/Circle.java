package com.huotu.huobanplus.sns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 圈子
 * Created by Administrator on 2016/9/28.
 */
@Entity
@Getter
@Setter
@Cacheable(value = false)
public class Circle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商家
     */
    private Long customerId;

    /**
     * 是否可用
     */
    private boolean enabled = true;

    /**
     * 圈子所属分类
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private Category category;

    /**
     * 圈子组长
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private User leader;

    /**
     * 圈子名称
     */
    @Column(length = 10)
    private String name;
    /**
     * 图片
     */
    @Column(length = 200)
    private String pictureUrl;

    /**
     * 简介
     */
    @Column(length = 500)
    private String summary;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /**
     * 是否热门推荐
     */
    private boolean suggested = true;

    /**
     * 圈子关注人数
     */
    private long userAmount = 0;

    /**
     * 文章数
     */
    private long articleAmount = 0;

    /**
     * 圈子标签
     */
    @OneToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<Tag> tags;
}
