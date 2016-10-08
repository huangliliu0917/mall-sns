package com.huotu.huobanplus.sns.model.common;

/**
 * 文章类型
 * Created by Administrator on 2016/10/8.
 */
public enum ArticleType {
    Normal(0, "普通文章"),
    Wiki(1, "百科文章");

    private int value;

    private String name;

    ArticleType(int value, String name) {
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
