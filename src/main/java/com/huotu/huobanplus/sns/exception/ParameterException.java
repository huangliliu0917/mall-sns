package com.huotu.huobanplus.sns.exception;

/**
 * Created by Administrator on 2016/11/3.
 */
public class ParameterException extends SnsException {
    public ParameterException(String message) {
        super(message);
    }

    public ParameterException(int code, String message) {
        super(code, message);
    }
}
