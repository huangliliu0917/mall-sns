package com.huotu.huobanplus.sns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 标签库
 * Created by Administrator on 2016/10/8.
 */
@Entity
@Cacheable(value = false)
@Getter
@Setter
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商家
     */
    private Long customerId;

    /**
     * 名称
     */
    @Column(length = 50)
    private String name;
}
