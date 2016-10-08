package com.huotu.huobanplus.sns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户关注的圈子
 * Created by Administrator on 2016/9/29.
 */
@Entity
@Cacheable(value = false)
@Getter
@Setter
public class UserCircle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商家
     */
    private Long customerId;

    /**
     * 用户
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private Circle circle;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
