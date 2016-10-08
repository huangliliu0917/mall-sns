package com.huotu.huobanplus.sns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 用户等级
 * Created by Administrator on 2016/9/30.
 */
@Entity
@Cacheable(value = false)
@Getter
@Setter
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商家
     */
    private Long customerId;

    /**
     * 等级名称
     */
    private String name;
    /**
     * 等级经验值
     */
    private Long exp;
}
