package com.huotu.huobanplus.sns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 用户
 * Created by Administrator on 2016/9/28.
 */
@Entity
@Cacheable(value = false)
@Getter
@Setter
public class User {

    @Id
    private Long id;

    /**
     * 商家
     */
    private Long customerId;

    /**
     * 圈子关注人数
     */
    private Long userAmount;

    /**
     * 粉丝数
     */
    private Long fansAmount;

    /**
     * 发布文章数
     */
    private Long articleAmount;

    /**
     * 等级 todo 评级标准
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private Level level;

    /**
     * 排名 （计算规则待定 todo）
     */
    private Long rank;
}
