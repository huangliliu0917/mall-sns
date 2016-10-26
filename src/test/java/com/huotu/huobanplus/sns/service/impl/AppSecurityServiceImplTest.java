package com.huotu.huobanplus.sns.service.impl;

import com.huotu.huobanplus.sns.boot.*;
import com.huotu.huobanplus.sns.service.AppSecurityService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;


/**
 * Created by Administrator on 2016/10/25.
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BootConfig.class, MvcConfig.class
        , ContainerRuntimeConfig.class
        , LocalRuntimeConfig.class
        , RedisConfig.class})
@ActiveProfiles("development")
@Transactional

public class AppSecurityServiceImplTest {

    private static Log log = LogFactory.getLog(AppSecurityServiceImplTest.class);

    @Autowired
    private AppSecurityService appSecurityService;

    @Test
    public void createJWT() throws Exception {
        String jwt = appSecurityService.createJWT("sns", "username", 10000);

        log.info(jwt);
        String subject = appSecurityService.parseJWT(jwt);
        log.info(subject);

    }

    @Test
    public void parseJWT() throws Exception {

    }

}