package com.huotu.huobanplus.sns.model.admin;

import com.huotu.huobanplus.sns.entity.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 后台显示圈子的model
 * Created by slt on 2016/10/12.
 */
@Setter
@Getter
public class CircleListModel {

    /**
     * 圈子ID
     */
    private Long circleId;

    /**
     * 圈子名称
     */
    private String circleName;

    /**
     * 圈子简介
     */
    private String summary;

    /**
     * 圈子图片url
     */
    private String pictureUrl;

    /**
     * 分类ID
     */
    private Integer categoryId;
    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 小组组长ID
     */
    private Long leaderId;

    /**
     * 小组组长名字
     */
    private String leaderName;

    /**
     * 创建时间
     */
    private String createDate;

    /**
     * 是否热门
     */
    private boolean suggested;

    /**
     * 是否启用
     */
    private boolean enabled;

    /**
     * 文章数
     */
    private long articleAmount;

    /**
     * 关注数
     */
    private long userAmount;

    /**
     * 分类标签
     */
    private List<Tag> tags;

}
