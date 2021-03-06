package com.huotu.huobanplus.sns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * 文章评论
 * Created by Administrator on 2016/9/28.
 */
@Entity
@Cacheable(value = false)
@Getter
@Setter
public class ArticleComment extends AbstractComment {

    /**
     * 所属文章
     */
    @ManyToOne
    private Article article;

}
