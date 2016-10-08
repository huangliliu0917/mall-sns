package com.huotu.huobanplus.sns.model.common;

/**
 * 文章状态
 * Created by Administrator on 2016/10/8.
 */
public enum  ArticleStatus {
    Normal(0, "正常"),
    Delete(1, "删除");

    private int value;

    private String name;

    ArticleStatus(int value, String name) {
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
