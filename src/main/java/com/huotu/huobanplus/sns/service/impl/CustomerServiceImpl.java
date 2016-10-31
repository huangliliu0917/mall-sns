/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service.impl;

import com.huotu.huobanplus.sns.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by jin on 2016/10/31.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private Environment environment;

    @Override
    public Long currentCustomerId(HttpServletRequest request) {
        Long defaultValue = null;
        if (environment.acceptsProfiles("develop") && !environment.acceptsProfiles("no_mock_customerid")) {
            defaultValue = 4471L;
        } else if (environment.acceptsProfiles("test") && !environment.acceptsProfiles("no_mock_customerid")) {
            defaultValue = 3447L;
        }

        if (request == null)
            return defaultValue;

        HttpSession session = request.getSession(true);
        Long savedCustomerId = (Long) session.getAttribute("__savedCustomerId");
        if (savedCustomerId != null) {
            defaultValue = savedCustomerId;
        }

        Cookie roleCookie = getCookie(request, "RoleID");
        if (roleCookie == null)
            return defaultValue;

        if ("-1".equals(roleCookie.getValue())) {
            String value = request.getParameter("customerid");
            if (value == null)
                return defaultValue;
            try {
                Long longValue = Long.parseLong(value);
                session.setAttribute("__savedCustomerId", longValue);
                return longValue;
            } catch (NumberFormatException ex) {
                return defaultValue;
            }
        }

        if ("-2".equals(roleCookie.getValue())) {
            Cookie userCookie = getCookie(request, "UserID");
            if (userCookie == null)
                return defaultValue;
            String value = userCookie.getValue();
            if (value == null)
                return defaultValue;
            try {
                Long longValue = Long.parseLong(value);
                session.setAttribute("__savedCustomerId", longValue);
                return longValue;
            } catch (NumberFormatException ex) {
                return defaultValue;
            }
        }
//        [RoleID]
//        -1->超级管理员，从URL上取
//                -2->商家，从[UserID]上
        //详解版本， 如果不存在RoleID cookie 则非法；存在RoleID 1 则认为是超级管理员 认可URL请求参数的customerId, 如果为2 则从 UserID cookie上获取。

        return defaultValue;
    }

    private Cookie getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() == null)
            return null;
        for (Cookie cookie : request.getCookies()) {
            if (name.equals(cookie.getName()))
                return cookie;
        }
        return null;
    }
}
