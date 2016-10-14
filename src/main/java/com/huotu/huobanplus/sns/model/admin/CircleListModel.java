package com.huotu.huobanplus.sns.model.admin;

import lombok.Getter;
import lombok.Setter;

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
     * 圈子图片url
     */
    private String pictureUrl;

    /**
     * 分类名称
     */
    private String categoryName;

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
    private String suggested;

    /**
     * 文章数
     */
    private long articleAmount;

    /**
     * 关注数
     */
    private long userAmount;

}
