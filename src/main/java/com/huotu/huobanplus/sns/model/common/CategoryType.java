package com.huotu.huobanplus.sns.model.common;

/**
 * 分类类型
 * Created by Administrator on 2016/10/8.
 */
public enum CategoryType {
    Normal(0, "普通"),
    Wiki(1, "百科");

    private int value;

    private String name;

    CategoryType(int value, String name) {
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
