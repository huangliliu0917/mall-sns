package com.huotu.huobanplus.sns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 文章
 * Created by Administrator on 2016/9/28.
 */
@Entity
@Cacheable(value = false)
@Getter
@Setter
public class Article extends AbstractArticle {

    /**
     * 所属圈子
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private Circle circle;

    /**
     * 所属分类(百科)
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private Category category;


    /**
     * 广告内容
     */
    @Column(length = 500)
    private String adConent;

}
