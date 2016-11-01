/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.model.common;

import com.huotu.common.api.ICommonEnum;

/**
 * Created by Administrator on 2016/10/8.
 */
public enum AppCode implements ICommonEnum {

    SUCCESS(1, "操作成功"),
    PARAMETER_ERROR(10001, "参数错误"),
    INTERNAL_SERVER_ERROR(10002, "内部服务器错误"),

    /**
     * 用户登录注册提示 以2开头
     **/
    ERROR_USER_NEED_LOGIN(20001, "用户需要登录"),
    ERROR_WEIXIN_LOGIN(20002, "微信登录失败，没有注册手机"),
    ERROR_WRONG_MOBILE(20003, "不合法的手机号"),

    ERROR_WRONG_VERIFICATION_INTERVAL(20104, "验证码发送间隔为90秒"),
    ERROR_SEND_MESSAGE_FAIL(20105, "短信发送通道不稳定，请重新尝试"),
    VERIFICATION_CODE_INVOID(20106, "验证码错误"),
    VERIFICATION_CODE_DUED(20107, "验证码到期了"),


    /**
     * 用户关注圈子，举报等操作以3开头
     */
    ERROR_CONCERN_ALREADY(30001, "您已关注该圈子"),
    ERROR_UNCONCERN_ALREADY(30002, "您已取消关注该圈子"),
    ERROR_CONCERNUSER_ALREADY(30101, "您已关注该用户"),
    ERROR_UNCONCERNUSER_ALREADY(30102, "您已经取消关注该用户"),

    ERROR_SENSITIVE_CONTENT(30201, "您发表的内容包含敏感词汇");

    /**
     * 圈子，百科等以4开头
     */


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
