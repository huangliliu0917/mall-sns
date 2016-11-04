package com.huotu.huobanplus.sns.exception;

/**
 * Created by Administrator on 2016/11/4.
 */
public class MobileNotExistException extends  SnsException {
    public MobileNotExistException(String message) {
        super(message);
    }

    public MobileNotExistException(int code, String message) {
        super(code, message);
    }
}
