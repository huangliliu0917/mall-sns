package com.huotu.huobanplus.sns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 用户文章
 * 用户发布文章时，需要同时对被关注的用户发布文章
 * <p>
 * Created by Administrator on 2016/10/8.
 */
@Entity
@Cacheable(value = false)
@Getter
@Setter
public class UserArticle extends AbstractArticle {


    /**
     * 收到文章的用户
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private User owner;
}
