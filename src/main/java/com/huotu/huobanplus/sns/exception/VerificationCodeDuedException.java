package com.huotu.huobanplus.sns.exception;

/**
 * Created by Administrator on 2016/10/31.
 */
public class VerificationCodeDuedException extends SnsException {
    public VerificationCodeDuedException(int code,String message) {
        super(code,message);
    }
}
