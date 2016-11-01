package com.huotu.huobanplus.sns.exception;

/**
 * Created by Administrator on 2016/10/31.
 */
public class VerificationCodeInvoidException extends SnsException {
    public VerificationCodeInvoidException(int code, String message) {
        super(code, message);
    }
}
