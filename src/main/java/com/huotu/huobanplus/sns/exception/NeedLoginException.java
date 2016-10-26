package com.huotu.huobanplus.sns.exception;

/**
 * 需要登录
 * Created by Administrator on 2016/10/26.
 */
public class NeedLoginException extends Exception {
    public NeedLoginException(String message) {
        super(message);
    }
}
