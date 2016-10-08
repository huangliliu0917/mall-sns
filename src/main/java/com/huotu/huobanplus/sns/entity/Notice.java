package com.huotu.huobanplus.sns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 公告
 * Created by Administrator on 2016/9/28.
 */
@Entity
@Cacheable(value = false)
@Getter
@Setter
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商家
     */
    private Long customerId;

    /**
     * 所属圈子
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private Circle circle;

    /**
     * 标题
     */
    @Column(length = 100)
    private String name;

    /**
     * 内容
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;

    /**
     * 发布时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /**
     * 启用
     */
    private Boolean enabled;
}
