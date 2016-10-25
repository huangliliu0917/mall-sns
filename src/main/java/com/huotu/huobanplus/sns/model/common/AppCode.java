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
    /**
     * SUCCESS(1, "操作成功")
     */
    SUCCESS(1, "操作成功"),
    /**
     * PARAMETER_ERROR(1001,"参数错误")
     */
    PARAMETER_ERROR(1001, "参数错误"),
    /**
     * ERROR_USER_NOT_FOUND(2001,"用户不存在")
     */
    ERROR_USER_NOT_FOUND(2001,"用户不存在"),

    /**
     * USER_BE_FREEZE(2101,"用户已被冻结，请联系客服")
     */
    USER_BE_FREEZE(2101,"用户已被冻结，无法进行任何操作，请联系客服"),

    /**
     * USER_BE_NOTALK(2102,"您已被禁言，无法发表任何言论")
     */
    USER_BE_NOTALK(2102,"您已被禁言，无法发表任何言论"),

    SYSTEM_BAD_REQUEST_50601(50601, "系统请求失败,code 50601"),//sign校验失败
    /**
     * ERROR_USER_LOGIN_FAIL(56000, "用户登录失败")
     */
    ERROR_USER_LOGIN_FAIL(56000, "用户登录失败"),
    /**
     * ERROR_USER_TOKEN_FAIL(56001, "用户登录失效，需要重新登录")
     */
    ERROR_USER_TOKEN_FAIL(56001, "你已经在其他设备登录，需要重新登录"),
    /**
     * ERROR_WRONG_MOBILE(53003, "不合法的手机号")
     */
    ERROR_WRONG_MOBILE(53003, "不合法的手机号"),

    /**
     *  ERROR_MOBILE_ALREADY_BINDING(53004, "手机号已绑定")
     */
    ERROR_MOBILE_ALREADY_BINDING(53004, "手机号已绑定"),
    /**
     *  ERROR_MOBILE_NOT_BINDING(53005, "手机号未绑定")
     */
    ERROR_MOBILE_NOT_BINDING(53005, "手机号未绑定"),
    /**
     * ERROR_WRONG_VERIFICATION_INTERVAL(53014, "验证码发送间隔为90秒")
     */
    ERROR_WRONG_VERIFICATION_INTERVAL(53014, "验证码发送间隔为90秒"),
    /**
     * ERROR_SEND_MESSAGE_FAIL(55001, "短信发送通道不稳定，请重新尝试")
     */
    ERROR_SEND_MESSAGE_FAIL(55001, "短信发送通道不稳定，请重新尝试"),

    /**
     * ERROR_WRONG_CODE(55002,"验证码错误")
     */
    ERROR_WRONG_CODE(55002,"验证码错误"),

    /**
     * ERROR_SHARE_NOT_FOUND(60001,"文章不存在")
     */
    ERROR_SHARE_NOT_FOUND(60001,"文章不存在"),

    /**
     * ERROR_COMMENT_NOT_FOUND(60002,"评论不存在")
     */
    ERROR_COMMENT_NOT_FOUND(60002, "评论不存在"),

    ERROR_ALREADY_CONCERNED(70001, "您已关注该圈子了");

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
