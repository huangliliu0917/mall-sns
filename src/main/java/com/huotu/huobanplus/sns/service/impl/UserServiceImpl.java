/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service.impl;

import com.huotu.common.base.RSAHelper;
import com.huotu.huobanplus.sns.entity.User;
import com.huotu.huobanplus.sns.entity.UserArticle;
import com.huotu.huobanplus.sns.entity.VerificationCode;
import com.huotu.huobanplus.sns.model.AppCircleArticleModel;
import com.huotu.huobanplus.sns.model.AppUserModel;
import com.huotu.huobanplus.sns.model.common.CodeType;
import com.huotu.huobanplus.sns.model.common.VerificationType;
import com.huotu.huobanplus.sns.repository.UserRepository;
import com.huotu.huobanplus.sns.repository.VerificationCodeRepository;
import com.huotu.huobanplus.sns.service.AppSecurityService;
import com.huotu.huobanplus.sns.service.CommonConfigService;
import com.huotu.huobanplus.sns.service.UserService;
import com.huotu.huobanplus.sns.utils.ContractHelper;
import com.huotu.huobanplus.sns.utils.CookieHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 用户
 * Created by Administrator on 2016/10/9.
 */
@Service
public class UserServiceImpl implements UserService {

    private static Log log = LogFactory.getLog(UserServiceImpl.class);
    private String userKey = "userId";

    private String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAK8Cd7gS6dOz3ALnICrlLOiGWv5RHgiQFvSsKMBudp59UDrsknSsaZsdBagrhMdtlKxYI1JzD2iJrGGBjPexvtGXVFrZjkLXZCdmeiM0L41m7VvkeI4ASD/4T3qxSjhMCRAVvJ0vC/sPffKR71In0hyUWMrFXCPR10zGUmcU9TwVAgMBAAECgYAeP1/vuZ0eUOTCv62onEmBus75S43UTwsYqLS2ZaEszV3TgVXiwnXSMFbs9PCTA1aB3w3jzy0nlTvs8lYp7VecWjG+rqayIZk2HtdKwNoEroqOPLgvDUTwxCC30CByZL4yb95XhNFBpd4p7cJLlPgf8M58WT7ttS3UquJDhYPYgQJBAPD8/HH07yMS17VfO6KwM7OCsnwUdrH3mGtK3ac86Z5xhelK4ikiKetu+1QFSeUOLm4Uv4K67c6lko+yPvmjqzUCQQC56VP0QhexQj4sylGtGSjqYGftRkkhbg6zHLURR0RMzTw+jXP8J6R0xTlIiBKJyK2xgAXuuqNUlyQVEJwCSolhAkEAmQ+F43c3P+ai3Q7MmMsjO1vCs25n6SciRts5JxRYKYtfC0rFlGyfhWpq9PWa9oHoWYCSFp1Vl4+wI9aJixM6FQJAA43vefsNgukWUTrpBts1Sg3fzsyKN2ZoR4pj99mZ97Hw1e1Ua1zCqyzeJIHdgN7iW0NsWZ0d5E8jdHel0/Fi4QJAJhb7Mt8pC5UCJNTJc1JQyzTUiZAvGr4EeTiAi0MCkPH85pOEr6ChMH4qI/51nTTqskoMJtd/xHxMDbEeXkrnAg==";
    private String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCvAne4EunTs9wC5yAq5Szohlr+UR4IkBb0rCjAbnaefVA67JJ0rGmbHQWoK4THbZSsWCNScw9oiaxhgYz3sb7Rl1Ra2Y5C12QnZnojNC+NZu1b5HiOAEg/+E96sUo4TAkQFbydLwv7D33yke9SJ9IclFjKxVwj0ddMxlJnFPU8FQIDAQAB";

    @Autowired
    private Environment env;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private CommonConfigService commonConfigService;

//    @Autowired
//    private com.huotu.huobanplus.common.service.UserService mallUserService;

    @Autowired
    private com.huotu.huobanplus.common.repository.UserRepository mallUserRepository;

//    @Override
//    public Long getMerchantUserId(HttpServletRequest request) {
//        if (env.acceptsProfiles("development") || env.acceptsProfiles("staging")) {
//            return 97278L;//146 4471商户 王明
////            return 96116L;
//        } else {
//            String encrypt = CookieHelper.get(request, userKey);
//            try {
//                Long userId = Long.parseLong(RSAHelper.decrypt(encrypt, privateKey));
//                if (userId > 0) return userId;
//                return null;
//            } catch (Exception ex) {
//                return null;
//            }
//        }
//    }
//
//
//    @Override
//    public void setUserId(Long userId, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        if (userId > 0) {
//            String encrypt = RSAHelper.encrypt(userId.toString(), publicKey);
//            CookieHelper.set(response, userKey, encrypt, request.getServerName(), 60 * 60 * 24 * 365);
//        }
//
//    }

    @Override
    public Page<User> findByNickNameAndAuthenticationIdAndLevelId(String nickName, Integer authenticationId,
                                                                  Long levelId, Pageable pageable) throws IOException {
        return userRepository.findAll((root, query, cb) -> {
            Predicate predicate = cb.and();
            if (StringUtils.hasText(nickName)) {
                predicate = cb.and(predicate, cb.like(root.get("nickName").as(String.class), "%" + nickName + "%"));
            }
            if (Objects.nonNull(authenticationId)) {
                predicate = cb.and(predicate, cb.equal(root.get("authenticationType").as(Integer.class), authenticationId));
            }
            if (Objects.nonNull(levelId)) {
                predicate = cb.and(predicate, cb.equal(root.get("level").get("id").as(Long.class), levelId));
            }
            return predicate;
        }, pageable);
    }


    public AppUserModel getAppUser(Long userId) {
        User user = userRepository.findOne(userId);
        if (user != null) {
            AppUserModel appUserModel = new AppUserModel();
            appUserModel.setFansAmount(user.getFansAmount());
            appUserModel.setRank(user.getRank());
            appUserModel.setUserAmount(user.getUserAmount());
            appUserModel.setUserName(user.getNickName());
            appUserModel.setUserHeadUrl(user.getImgURL());
            return appUserModel;
        }
        return null;
    }

    private AppCircleArticleModel changeModel(UserArticle article) throws IOException {
        AppCircleArticleModel model = new AppCircleArticleModel();
        model.setPid(article.getArticleId());
        model.setName(article.getName());
        model.setPictureUrl(article.getPictureUrl());
//        model.setUrl("");
        model.setUserName(article.getPublisherNickname());
        model.setUserHeadUrl(article.getPublisherHeaderImageUrl());
        model.setUserLevel(article.getPublisherLevelId());
        model.setTime(article.getDate().getTime());
//        model.setCommentsAmount(article.geta);
        BoundHashOperations<String, String, Long> articleOperations = redisTemplate
                .boundHashOps(ContractHelper.articleFlag + article.getArticleId());
        articleOperations.putIfAbsent("comments", 0L);
        model.setCommentsAmount(articleOperations.get("comments"));
        //浏览量不知道怎么统计
        return model;
    }

    @Override
    public AppCircleArticleModel[] changeModelArray(List<UserArticle> articles) throws IOException {
        AppCircleArticleModel[] models = new AppCircleArticleModel[articles.size()];
        for (int i = 0; i < articles.size(); i++) {
            models[i] = changeModel(articles.get(i));
        }
        return models;
    }

    @Autowired
    private AppSecurityService appSecurityService;

    public String userLogin(Long customerId, String phone, String code
            , String openId
            , String nickName
            , String imageUrl) {
//        //判断验证码是否有效
//        VerificationCode verificationCode = verificationCodeRepository.findByMobileAndTypeAndCodeType(phone, VerificationType.BIND_REGISTER, CodeType.text);
//        if (verificationCode == null) throw new IllegalStateException("验证码无效");
//
//        Date currentDate = new Date();
//        if (currentDate.getTime() - verificationCode.getSendTime().getTime() < 60 * 60 * 1000) {
//            throw new IllegalStateException("验证码失效");//超过1小时
//        }
//
//        if (!code.equals(verificationCode.getCode())) throw new IllegalStateException("验证码错误");
//
//        com.huotu.huobanplus.common.entity.User mallUser = mallUserRepository.findByMerchantIdAndMobile(customerId, phone);
//        Long userId;
//        //商城中判断用户是否存在
//        if (mallUser == null) {
//            userId = mallUserService.userRegister(customerId, phone);
//        } else {
//            userId = mallUser.getId();
//        }
//
//        //判断本地用户是否存在，不存在则创建本地用户
//        User user = userRepository.findOne(userId);
//        if (user == null) {
//            user = register(customerId, phone, openId, nickName, imageUrl);
//        }
//        //返回token
//        String token = appSecurityService.createJWT("sns", customerId.toString() + "," + user.getId().toString(), 1000 * 3600 * 24 * 30);
//
//        return token;
        return null;
    }

    @Override
    public String weixinLogin(Long customerId, String openId, String nickName, String imageUrl) {
        return null;
    }


    public User register(Long customerId
            , String mobile
            , String openId
            , String nickName
            , String imageUrl) {
        User user = new User();
        user.setCustomerId(customerId);
        user.setMobile(mobile);
        user.setOpenId(openId);
        user.setNickName(nickName);
        user.setImgURL(imageUrl);
        user.setCreateDate(new Date());
//            user.setLevel();//todo 获取最低级别
        user.setRank(100000000L);
//            user.setTags();
        user = userRepository.save(user);
        return user;
    }
}
