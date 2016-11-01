package com.huotu.huobanplus.sns.mallservice;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2016/10/31.
 */
public interface MallUserService {

    Long userRegister(Long customerId, String phone
            , String openId
            , String nickName
            , String imageUrl) throws UnsupportedEncodingException;
}
