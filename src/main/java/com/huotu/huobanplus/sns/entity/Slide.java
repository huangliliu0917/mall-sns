package com.huotu.huobanplus.sns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 圈子头部的banner图
 * Created by Administrator on 2016/9/29.
 */
@Entity
@Getter
@Setter
@Cacheable(value = false)
public class Slide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商家
     */
    private Long customerId;


    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private Circle circle;

    /**
     * 图片地址
     */
    @Column(length = 200)
    private String pictureUrl;

    /**
     * 跳转地址
     */
    @Column(length = 200)
    private String url;
}
