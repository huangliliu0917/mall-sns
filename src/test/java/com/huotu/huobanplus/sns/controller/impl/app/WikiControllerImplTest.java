/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.controller.impl.app;

import com.huotu.huobanplus.sns.base.BaseTest;
import com.huotu.huobanplus.sns.base.Device;
import com.huotu.huobanplus.sns.base.DeviceType;
import com.huotu.huobanplus.sns.boot.*;
import com.huotu.huobanplus.sns.entity.Category;
import com.huotu.huobanplus.sns.mallservice.MallUserService;
import com.huotu.huobanplus.sns.model.AppCategoryModel;
import com.huotu.huobanplus.sns.model.AppWikiListModel;
import com.huotu.huobanplus.sns.model.common.ArticleType;
import com.huotu.huobanplus.sns.model.common.CategoryType;
import com.huotu.huobanplus.sns.service.AppSecurityService;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;

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
public class WikiControllerImplTest extends BaseTest {


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
        mockUserId = mallUserService.userRegister(customerId, mockMobile, "", "", "", "");
        createUser(customerId, mockUserId, mockMobile, "", "", "");
        String token = appSecurityService.createJWT("sns", customerId + "," + mockUserId, 1000 * 3600 * 24 * 30);
        device.setToken(token);
        device.setCustomerId(customerId);
    }

    @Test
    public void getCatalogList() throws Exception {
        Category category = createCategory();

        int i = 0;
        while (i < 10) {
            createCategory(CategoryType.Wiki, category);
            i++;
        }
        String contentAsString = mockMvc.perform(device.getApi("/wiki/getCatalogList")
                .param("id", category.getId().toString()).build())
                .andDo(print()).andReturn().getResponse().getContentAsString();
        List<AppCategoryModel> list = JsonPath.read(contentAsString, "$.resultData.catalogList");
        Assert.assertEquals("记录数相同", 10, list.size());
    }


    @Test
    public void wikiList() throws Exception {
        Category category = createCategory(CategoryType.Wiki, null);
        int i = 0;
        while (i < 16) {
            createArticle(ArticleType.Wiki, mockUserId, category.getId(), 0L);
            i++;
        }

        String contentAsString = mockMvc.perform(device.getApi("/wiki/wikiList")
                .param("catalogId", category.getId().toString())
                .param("lastId", "0")
                .build())
                .andDo(print()).andReturn().getResponse().getContentAsString();

        List list = JsonPath.read(contentAsString, "$.resultData.wikilist");

        Assert.assertEquals("记录数相同", 10, list.size());
        contentAsString = mockMvc.perform(device.getApi("/wiki/wikiList")
                .param("catalogId", category.getId().toString())
                .param("lastId", JsonPath.read(contentAsString, "$.resultData.wikilist[9].pid").toString())
                .build())
                .andDo(print()).andReturn().getResponse().getContentAsString();

        list = JsonPath.read(contentAsString, "$.resultData.wikilist");
        Assert.assertEquals("记录数相同", 6, list.size());
    }

    @Test
    public void wiki() throws Exception {
        Category category = createCategory(CategoryType.Wiki, null);
        Article article = createArticle(ArticleType.Wiki, mockUserId, category.getId(), 0L);
        String contentAsString = mockMvc.perform(device.getApi("/wiki/wiki")
                .param("id", article.getId().toString())
                .build())
                .andDo(print()).andReturn().getResponse().getContentAsString();
        Assert.assertEquals(true, JsonPath.read(contentAsString, "$.resultData.data") != null);
    }


}