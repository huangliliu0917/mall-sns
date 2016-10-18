/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanplus.sns.service.impl;

import com.huotu.huobanplus.sns.service.CommonConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * 系统配置项
 * Created by lgh on 2015/9/23.
 */

@Service
public class CommonConfigServiceImpl implements CommonConfigService {

    @Autowired
    Environment env;


    @Override
    public String getWebUrl() {
        return env.getProperty("mallsns.weburl", "http://localhost:8986");
    }

    @Override
    public String getAuthWebUrl() {
        return env.getProperty("auth.web.url", "http://test.auth.huobanplus.com:8081");
    }


    @Override
    public String getAuthKeySecret() {
        return env.getProperty("auth.appsecrect", "1165a8d240b29af3f418b8d10599d0dc");
    }


    @Override
    public String getResourcesUri() {
        return env.getProperty("huotu.resourcesUri", "http://localhost:8986");
    }

    @Override
    public String getResourcesHome() {
        return env.getProperty("huotu.resourcesHome", "");
    }

}
