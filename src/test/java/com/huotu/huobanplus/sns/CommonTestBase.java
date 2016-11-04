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
import com.huotu.huobanplus.sns.entity.*;
import com.huotu.huobanplus.sns.mallservice.MallUserService;
import com.huotu.huobanplus.sns.model.AppArticleCommentModel;
import com.huotu.huobanplus.sns.repository.*;
import com.huotu.huobanplus.sns.service.AppSecurityService;
import com.huotu.huobanplus.sns.service.CircleService;
import com.huotu.huobanplus.sns.service.SlideService;
import com.huotu.huobanplus.sns.service.UserCircleService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by jin on 2016/11/2.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@WebAppConfiguration
@ActiveProfiles({"develop", "development"})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringTestConfig.class})
@Transactional
public abstract class CommonTestBase extends BaseTest {

    protected static Random random = new Random();
    /**
     * 携带token的设备
     */
    public Device device;
    public Long mockUserId;
    public String mockMobile;
    protected User user;
    @Autowired
    protected ArticleCommentRepository articleCommentRepository;
    @Autowired
    protected ArticleRepository articleRepository;
    @Autowired
    protected CircleRepository circleRepository;
    @Autowired
    protected UserCircleRepository userCircleRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ConcernRepository concernRepository;
    @Autowired
    protected UserArticleRepository userArticleRepository;
    @Autowired
    protected SlideRepository slideRepository;
    @Autowired
    protected SlideService slideService;
    @Autowired
    protected CircleService circleService;
    @Autowired
    protected RedisTemplate<String, AppArticleCommentModel> articleCommentRedisTemplate;
    @Autowired
    protected ReportRepository reportRepository;
    @Autowired
    private MallUserService mallUserService;
    @Autowired
    private AppSecurityService appSecurityService;
    @Autowired
    protected UserCircleService userCircleService;

    @Before
    public void prepareDevice() throws UnsupportedEncodingException {
        device = Device.newDevice(DeviceType.Android);
        mockMobile = generateMobile();
        mockUserId = mallUserService.userRegister(customerId, mockMobile, "", "", "", "");
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

    protected Article randomArticle() throws Exception {
        Article article = new Article();
        article.setContent(UUID.randomUUID().toString());
        article.setPublisher(user);
        articleRepository.saveAndFlush(article);
        return article;
    }

    protected Circle randomCircle() throws Exception {
        Circle circle = new Circle();
        circle.setCustomerId(user.getCustomerId());
        circle.setName(UUID.randomUUID().toString().substring(0, 9));
        circleRepository.saveAndFlush(circle);
        return circle;
    }

    protected User randomUser() throws Exception {
        Random random = new Random();
        User newUser = new User();
        newUser.setId(100000 + random.nextLong());
        newUser.setNickName(UUID.randomUUID().toString());
        newUser.setCustomerId(user.getCustomerId());
        newUser.setOpenId(UUID.randomUUID().toString());
        userRepository.saveAndFlush(newUser);
        return newUser;
    }

    protected Concern randomConcern(User concernUser) throws Exception {
        Concern concern = new Concern();
        concern.setCustomerId(user.getCustomerId());
        concern.setToUser(user);
        concern.setUser(concernUser);
        concern.setDate(new Date());
        concernRepository.saveAndFlush(concern);
        return concern;
    }
}
