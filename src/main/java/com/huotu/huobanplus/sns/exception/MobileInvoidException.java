package com.huotu.huobanplus.sns.exception;

/**
 * Created by Administrator on 2016/11/4.
 */
public class MobileInvoidException extends SnsException {
    public MobileInvoidException(String message) {
        super(message);
    }

    public MobileInvoidException(int code, String message) {
        super(code, message);
    }
}
