package com.huotu.huobanplus.sns.exception;

/**
 * Created by Administrator on 2016/11/2.
 */
public class NotSupportVoiceException extends SnsException {
    public NotSupportVoiceException(String message) {
        super(message);
    }

    public NotSupportVoiceException(int code, String message) {
        super(code, message);
    }
}
