package com.huotu.huobanplus.sns.exception;

/**
 * Created by Administrator on 2016/11/4.
 */
public class UserNamePasswordInvoidException extends SnsException {
    public UserNamePasswordInvoidException(String message) {
        super(message);
    }

    public UserNamePasswordInvoidException(int code, String message) {
        super(code, message);
    }
}
