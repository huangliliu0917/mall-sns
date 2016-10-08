package com.huotu.huobanplus.sns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 科普 百科
 * <p>
 * 用户达到一定级别可发布科普
 * Created by Administrator on 2016/9/29.
 */
@Entity
@Cacheable(value = false)
@Getter
@Setter
public class Wiki extends AbstractArticle {



    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private Catalog catalog;

    /**
     * 推荐专家
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private User suggestExperts;

    /**
     * 广告内容
     */
    @Column(length = 500)
    private String adConent;
}
