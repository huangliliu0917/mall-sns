package com.huotu.huobanplus.sns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/29.
 */

@MappedSuperclass
@Getter
@Setter
public class AbstractComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商家
     */
    private Long customerId;

    /**
     * 评论人
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private User user;

    /**
     * 评论内容
     */
    @Column(length = 400)
    private String content;

    /**
     * 评论时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /**
     * 点赞量
     */
    private Long click;
}
