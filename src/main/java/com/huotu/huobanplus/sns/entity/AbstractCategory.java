package com.huotu.huobanplus.sns.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Administrator on 2016/9/29.
 */
public abstract class AbstractCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商家
     */
    private Long customerId;


    /**
     * 分类名称
     */
    @Column(length = 10)
    private String name;

    /**
     * 排序
     */
    private Integer sort;
}
