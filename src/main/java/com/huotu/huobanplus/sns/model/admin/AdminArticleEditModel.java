package com.huotu.huobanplus.sns.model.admin;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * Created by Administrator on 2016/10/14.
 */
@Getter
@Setter
public class AdminArticleEditModel {
    private String type;
    private Long id;
    private String name;
    private Long userId;
    private String userName;
    private Integer articleType;
    private String pictureUrl;
    private String pictureFullUrl;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 简介
     */
    private String summary;

//    /**
//     * 分类列表
//     */
//    private List<AdminBaseCategoryModel> categories;
//    /**
//     * 圈子列表
//     */
//    private List<AdminBaseCategoryModel> circles;

    /**
     * 分类
     */
    private Integer categoryId;
    /**
     * 圈子
     */
    private Long circleId;

    private String categoryName;

    private String circleName;

    private String adConent;
}
