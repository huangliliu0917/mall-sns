package com.huotu.huobanplus.sns.entity;

import com.huotu.huobanplus.sns.model.common.ArticleType;
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
public class AbstractArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 商户ID
     */
    private Long customerId;

    /**
     * 文章类型
     */
    private ArticleType articleType;

    /**
     * 文章标题
     */
    @Column(length = 100)
    private String name;

    /**
     * 图片地址
     */
    @Column(length = 200)
    private String pictureUrl;


    /**
     * 简介
     */
    @Column(length = 500)
    private String summary;

    /**
     * 发布时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

}
