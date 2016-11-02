package com.huotu.huobanplus.sns.exception;

/**
 * 第三方错误
 * <p>该错误发生无需跟用户汇报，但需要跟运营人士汇报</p>
 * @author CJ
 */
public class MessageInternetException extends SnsException{

    public MessageInternetException(String message) {
        super(message);
    }

    public MessageInternetException(int code, String message) {
        super(code, message);
    }
}
