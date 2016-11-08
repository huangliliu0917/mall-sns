package com.huotu.huobanplus.sns.service.impl;

import com.huotu.huobanplus.sns.service.AppSecurityService;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2016/10/25.
 */
@Service
public class AppSecurityServiceImpl implements AppSecurityService {

    private static String secret = "1165a8d240b29af3f418b8d10599dash";

    public String createJWT(String issuer, String subject, long expirationTime) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(UUID.randomUUID().toString())
                .setIssuedAt(now) //签发时间
                .setSubject(subject) //所面向的用户
                .setIssuer(issuer) //签发者
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (expirationTime >= 0) {
            long expMillis = nowMillis + expirationTime;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);//过期时间
        }

        //Builds the JWT and serializes it to a compact, URL-safe string

        return builder.compact();
    }

    public String parseJWT(String jwt) throws ExpiredJwtException {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .parseClaimsJws(jwt).getBody();
        return claims.getSubject();
//        System.out.println("ID: " + claims.getId());
//        System.out.println("Subject: " + claims.getSubject());
//        System.out.println("Issuer: " + claims.getIssuer());
//        System.out.println("Expiration: " + claims.getExpiration());
    }

    public String getMerchantUserId(HttpServletRequest request) {
        try {
            String authentication = request.getHeader("authentication");
            if (!StringUtils.isEmpty(authentication)) return parseJWT(authentication);
        } catch (Exception ex) {
        }
        return null;
    }
}
