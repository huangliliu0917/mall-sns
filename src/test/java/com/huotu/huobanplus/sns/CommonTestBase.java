/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns;

import com.huotu.huobanplus.sns.base.BaseTest;
import com.huotu.huobanplus.sns.base.Device;
import com.huotu.huobanplus.sns.base.DeviceType;
import com.huotu.huobanplus.sns.entity.Article;
import com.huotu.huobanplus.sns.entity.ArticleComment;
import com.huotu.huobanplus.sns.entity.User;
import com.huotu.huobanplus.sns.mallservice.MallUserService;
import com.huotu.huobanplus.sns.repository.ArticleCommentRepository;
import com.huotu.huobanplus.sns.repository.ArticleRepository;
import com.huotu.huobanplus.sns.service.AppSecurityService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * Created by jin on 2016/11/2.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@WebAppConfiguration
@ActiveProfiles({ "develop", "development"})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringTestConfig.class})
@Transactional
public abstract class CommonTestBase extends BaseTest {

    public User user;
    /**
     * 携带token的设备
     */
    public Device device;
    public Long mockUserId;
    public String mockMobile;
    @Autowired
    private ArticleCommentRepository articleCommentRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private MallUserService mallUserService;
    @Autowired
    private AppSecurityService appSecurityService;

    @Before
    public void prepareDevice() throws UnsupportedEncodingException {
        device = Device.newDevice(DeviceType.Android);
        mockMobile = generateMobile();
        mockUserId = mallUserService.userRegister(customerId, mockMobile, "", "", "");
        user = createUser(customerId, mockUserId, mockMobile, "", "", "");
        String token = appSecurityService.createJWT("sns", customerId + "," + mockUserId, 1000 * 3600 * 24 * 30);
        device.setToken(token);
        device.setCustomerId(customerId);
    }

    public ArticleComment randomArticleComment(Article article, ArticleComment articleComment) throws Exception {
        ArticleComment comment = new ArticleComment();
        comment.setArticle(article);
//        comment.set
        return null;
    }

    public Article randomArticle() throws Exception {
        Article article = new Article();
        article.setContent(UUID.randomUUID().toString());
        article.setPublisher(user);
        articleRepository.saveAndFlush(article);
        return article;
    }
}
