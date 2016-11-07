package com.huotu.huobanplus.sns.controller.impl.app;

import com.huotu.huobanplus.sns.base.BaseTest;
import com.huotu.huobanplus.sns.base.Device;
import com.huotu.huobanplus.sns.base.DeviceType;
import com.huotu.huobanplus.sns.boot.*;
import com.huotu.huobanplus.sns.entity.Category;
import com.huotu.huobanplus.sns.entity.User;
import com.huotu.huobanplus.sns.mallservice.MallUserService;
import com.huotu.huobanplus.sns.model.common.ArticleType;
import com.huotu.huobanplus.sns.model.common.CategoryType;
import com.huotu.huobanplus.sns.repository.ArticleRepository;
import com.huotu.huobanplus.sns.repository.UserRepository;
import com.huotu.huobanplus.sns.service.AppSecurityService;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;


import java.io.UnsupportedEncodingException;
import java.util.List;
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
        mockUserId = mallUserService.userRegister(customerId, mockMobile,"", "", "", "");
        createUser(customerId, mockUserId, mockMobile, "", "", "");
        String token = appSecurityService.createJWT("sns", customerId + "," + mockUserId, 1000 * 3600 * 24 * 30);
        device.setToken(token);
        device.setCustomerId(customerId);
    }

    @Test
    public void user() throws Exception {
        String contentAsString = mockMvc.perform(device.getApi("/web/user")
                .param("id", mockUserId.toString())
                .build())
                .andDo(print()).andReturn().getResponse().getContentAsString();
        Assert.assertEquals(true, JsonPath.read(contentAsString, "$.resultData.data") != null);
    }

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    @Transactional()
    public void userArticleList() throws Exception {
        Category category = createCategory(CategoryType.Normal, null);
        int i = 0;
        while (i < 16) {
            createArticle(ArticleType.Wiki, mockUserId, category.getId(), 0L);
            i++;
        }

        log.info("test amount:" + articleRepository.count());

        String contentAsString = mockMvc.perform(device.getApi("/web/userArticleList")
                .param("id", mockUserId.toString())
                .param("lastId", "0")
                .build())
                .andDo(print()).andReturn().getResponse().getContentAsString();

        List list = JsonPath.read(contentAsString, "$.resultData.articleList");

        Assert.assertEquals("记录数相同", 10, list.size());
        contentAsString = mockMvc.perform(device.getApi("/web/userArticleList")
                .param("id", mockUserId.toString())
                .param("lastId", JsonPath.read(contentAsString, "$.resultData.articleList[9].pid").toString())
                .build())
                .andDo(print()).andReturn().getResponse().getContentAsString();

        list = JsonPath.read(contentAsString, "$.resultData.articleList");
        Assert.assertEquals("记录数相同", 6, list.size());
    }

    @Test
    public void search() throws Exception {
        String contentAsString = mockMvc.perform(device.getApi("/web/search")
                .param("key", "无数据")
                .param("lastId", "0")
                .build())
                .andDo(print()).andReturn().getResponse().getContentAsString();

    }
}