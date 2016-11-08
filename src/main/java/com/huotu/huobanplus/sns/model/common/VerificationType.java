package com.huotu.huobanplus.sns.model.common;

import com.huotu.common.api.ICommonEnum;

/**
 * 1 注册
 */
public enum VerificationType implements ICommonEnum {

    BIND_REGISTER(1, "注册");

    private int value;

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

    private String name;

    VerificationType(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
