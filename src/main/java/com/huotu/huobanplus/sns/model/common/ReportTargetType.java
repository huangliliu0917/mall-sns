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
