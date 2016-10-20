package com.huotu.huobanplus.sns.model.common;

/**
 * Created by Administrator on 2016/10/20.
 */
public enum TagsType {
    User(0, "用户标签"),
    Circle(1, "圈子标签"),
    Article(2, "内容标签"),
    Goods(3, "商品标签");

    private int value;

    private String name;

    TagsType(int value, String name) {
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
