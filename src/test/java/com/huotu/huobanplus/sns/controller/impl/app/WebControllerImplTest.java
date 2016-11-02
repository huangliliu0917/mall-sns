package com.huotu.huobanplus.sns.controller.impl.app;

import com.huotu.huobanplus.sns.base.BaseTest;
import com.huotu.huobanplus.sns.base.Device;
import com.huotu.huobanplus.sns.base.DeviceType;
import com.huotu.huobanplus.sns.boot.*;
import com.huotu.huobanplus.sns.entity.User;
import com.huotu.huobanplus.sns.mallservice.MallUserService;
import com.huotu.huobanplus.sns.repository.UserRepository;
import com.huotu.huobanplus.sns.service.AppSecurityService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by Administrator on 2016/11/1.
 */

@WebAppConfiguration
@ActiveProfiles({"development"})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BootConfig.class
        , MvcConfig.class
        , MallBootConfig.class
        , ContainerRuntimeConfig.class
        , LocalRuntimeConfig.class
        , RedisConfig.class})
@Transactional
public class WebControllerImplTest extends BaseTest {


    private static Log log = LogFactory.getLog(WebControllerImplTest.class);

    /**
     * 携带token的设备
     */
    private Device device;

    @Autowired
    private MallUserService mallUserService;

    private Long mockUserId;

    private String mockMobile;

    @Autowired
    private AppSecurityService appSecurityService;


    @Before
    public void prepareDevice() throws UnsupportedEncodingException {
        device = Device.newDevice(DeviceType.Android);
        mockMobile = generateMobile();
        mockUserId = mallUserService.userRegister(customerId, mockMobile, "", "", "");
        createUser(customerId, mockUserId, mockMobile, "", "", "");
        String token = appSecurityService.createJWT("sns", customerId + "," + mockUserId, 1000 * 3600 * 24 * 30);
        device.setToken(token);
        device.setCustomerId(customerId);
    }

    @Test
    public void user() throws Exception {

    }

    @Test
    public void userArticleList() throws Exception {

    }

    @Test
    public void search() throws Exception {

    }
}