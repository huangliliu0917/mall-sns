package com.huotu.huobanplus.sns.exception;

/**
 * Created by Administrator on 2016/11/2.
 */
public class VericationCodeIntervalException extends  SnsException {
    public VericationCodeIntervalException(String message) {
        super(message);
    }

    public VericationCodeIntervalException(int code, String message) {
        super(code, message);
    }
}
