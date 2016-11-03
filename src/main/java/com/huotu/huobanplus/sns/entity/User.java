/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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
     * 手机号
     */
    @Column(length = 11)
    private String mobile;

    /**
     * 微信openid
     */
    @Column(length = 100)
    private String openId;
    /**
     * 昵称
     */
    @Column(length = 50)
    private String nickName;
    /**
     * 头像
     */
    @Column(length = 200)
    private String imgURL;
    /**
     * 关注人数
     */
    private long userAmount;
    /**
     * 粉丝数
     */
    private long fansAmount;
    /**
     * 发布文章数
     */
    private long articleAmount;
    /**
     * 经验
     */
    private long experience;
    /**
     * 注册时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    /**
     * 等级 todo 评级标准
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private Level level;

    /**
     * 身份
     * <p>
     * //     * @see {@link com.huotu.huobanplus.sns.entity.support.AuthenticationType}
     */
    private int authenticationType;

    /**
     * 排名 （计算规则待定 todo）
     */
    private Long rank;

    /**
     * 权限：**，两位，0代表禁用，1：代表可用
     * 第一位：发帖权限，第二位：评论权限
     */
    @Column(length = 100)
    private String power = "11";

    /**
     * 用户标签
     */
    @OneToMany(cascade = {CascadeType.REFRESH})
    private Set<Tag> tags;
}
