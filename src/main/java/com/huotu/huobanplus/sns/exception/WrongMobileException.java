package com.huotu.huobanplus.sns.exception;

/**
 * Created by Administrator on 2016/11/2.
 */
public class WrongMobileException extends  SnsException {
    public WrongMobileException(String message) {
        super(message);
    }

    public WrongMobileException(int code, String message) {
        super(code, message);
    }
}
