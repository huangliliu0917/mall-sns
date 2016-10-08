package com.huotu.huobanplus.sns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/9/29.
 */

@MappedSuperclass
@Getter
@Setter
public class AbstractCategory {

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
