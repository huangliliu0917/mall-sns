package com.huotu.huobanplus.sns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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
    private Integer id;

    /**
     * 商家
     */
    private Long customerId;

    /**
     * 圈子所属分类
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private Category category;

    /**
     * 圈子组长
     */
    private Long leader;

    /**
     * 分类名称
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
    private Boolean suggested;

    /**
     * 圈子关注人数
     */
    private Long userAmount;

    /**
     * 文章数
     */
    private Long articleAmount;
}
