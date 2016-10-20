package com.huotu.huobanplus.sns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 用户文章
 * 用户发布文章时，需要同时对被关注的用户发布文章
 * todo 暂时舍弃 考虑采用redis进行处理
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
    private Long ownerId;

    /**
     * 发布用户Id
     */
    private Long publisherId;
    /**
     * 发布用户昵称
     */
    private String publisherNickname;

    /**
     * 发布用户头像
     */
    private String publisherHeaderImageUrl;

    /**
     * 发布用户等级
     */
    private Long publisherLevelId;

    /**
     * 发布用户身份等级
     */
    private Integer publisherAuthenticationId;
}
