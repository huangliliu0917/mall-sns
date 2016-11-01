package com.huotu.huobanplus.sns.exception;

/**
 * Created by Administrator on 2016/10/31.
 */
public class WeixinLoginFailException extends SnsException {
    public WeixinLoginFailException(int code, String message) {
        super(code, message);
    }
}
