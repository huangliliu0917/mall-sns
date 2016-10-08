package com.huotu.huobanplus.sns.model.common;

/**
 * 身份类型
 * Created by Administrator on 2016/10/8.
 */
public enum AuthenticationType {
    Leader(0, "组长"),
    Manager(1, "管理员");

    private int value;

    private String name;

    AuthenticationType(int value, String name) {
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
