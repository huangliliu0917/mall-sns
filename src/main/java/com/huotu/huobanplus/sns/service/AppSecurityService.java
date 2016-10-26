package com.huotu.huobanplus.sns.service;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * Created by Administrator on 2016/10/25.
 */
public interface AppSecurityService {

    /**
     * 生成JWT
     *
     * @param issuer 发放者
     * @param subject 所面向的用户
     * @param expirationTime 到期时间(毫秒)
     * @return
     */
    String createJWT(String issuer, String subject, long expirationTime);

    String parseJWT(String jwt) throws ExpiredJwtException;
}
