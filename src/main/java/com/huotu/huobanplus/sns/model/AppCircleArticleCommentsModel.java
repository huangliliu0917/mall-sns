/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/9/29.
 */
@Getter
@Setter
public class AppCircleArticleCommentsModel implements Serializable {
    /**
     * 评论id
     */
    private Long pid;
    /**
     * 评论用户名称
     */
    private String userName;
    /**
     * 评论用户头像
     */
    private String userHeadUrl;
    /**
     * 点赞量
     */
    private Long clickAmount;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论日期
     */
    private Long date;

    /**
     * 扩展信息 (评论的评论)
     * 评论的评论内容无限循环冗余 以json格式存储
     * {fromId:,id:,content:,date:,userName:,userPictureUrl:,data:[{fromId:,id:,content:,date:,userName:,userPictureUrl:}]}
     */
//    private String extend;

    private List<AppArticleCommentModel> list;

    /**
     * 楼层
     */
    private Long floor;
}
