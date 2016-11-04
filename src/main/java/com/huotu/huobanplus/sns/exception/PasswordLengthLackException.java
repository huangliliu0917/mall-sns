package com.huotu.huobanplus.sns.exception;

/**
 * Created by Administrator on 2016/11/4.
 */
public class PasswordLengthLackException  extends SnsException {
    public PasswordLengthLackException(String message) {
        super(message);
    }

    public PasswordLengthLackException(int code, String message) {
        super(code, message);
    }
}
