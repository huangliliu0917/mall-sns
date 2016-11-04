package com.huotu.huobanplus.sns.exception;

/**
 * Created by Administrator on 2016/11/4.
 */
public class MobileExistException extends SnsException {
    public MobileExistException(String message) {
        super(message);
    }

    public MobileExistException(int code, String message) {
        super(code, message);
    }
}
