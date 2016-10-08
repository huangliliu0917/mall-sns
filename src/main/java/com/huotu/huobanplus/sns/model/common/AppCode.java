package com.huotu.huobanplus.sns.model.common;

import com.huotu.common.api.ICommonEnum;

/**
 * Created by Administrator on 2016/10/8.
 */
public enum AppCode implements ICommonEnum {
    /**
     * PARAMETER_ERROR(1001,"参数错误")
     */
    PARAMETER_ERROR(1001, "参数错误");

    private int value;

    private String name;

    AppCode(int value, String name) {
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
