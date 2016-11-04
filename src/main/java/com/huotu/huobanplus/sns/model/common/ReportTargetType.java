/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.model.common;

/**
 * 举报目标类型
 * Created by Administrator on 2016/10/8.
 */
public enum ReportTargetType {
    User(0, "用户"),
    Article(1, "文章"),
    Comment(2, "回复");

    private int value;

    private String name;

    ReportTargetType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static ReportTargetType getType(int value) {
        switch (value) {
            case 0:
                return ReportTargetType.User;
            case 1:
                return ReportTargetType.Article;
            case 2:
                return ReportTargetType.Comment;
            default:
                return ReportTargetType.User;
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
