/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.exception;

/**
 * Created by jin on 2016/11/1.
 * 内容异常
 */
public class ContentException extends SnsException {

    public ContentException(String message) {
        super(message);
    }

    public ContentException(int code, String message) {
        super(code, message);
    }
}
