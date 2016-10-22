/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service.impl;

import com.huotu.huobanplus.sns.entity.Concern;
import com.huotu.huobanplus.sns.entity.User;
import com.huotu.huobanplus.sns.exception.ConcernException;
import com.huotu.huobanplus.sns.exception.LogException;
import com.huotu.huobanplus.sns.model.AppUserConcermListModel;
import com.huotu.huobanplus.sns.repository.ConcernRepository;
import com.huotu.huobanplus.sns.repository.UserRepository;
import com.huotu.huobanplus.sns.service.ConcernService;
import com.huotu.huobanplus.sns.utils.ContractHelper;
import com.huotu.huobanplus.sns.utils.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by jin on 2016/10/10.
 */
@Service
public class ConcernServiceImpl implements ConcernService {

    private final static String userFlag = "_user_";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConcernRepository concernRepository;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public synchronized void concernUser(Long id) throws ConcernException, LogException, IOException {
        User user = UserHelper.getUser();
        User toUser = userRepository.getOne(id);
        List<Concern> concerns = concernRepository.findByUserAndToUser(user, toUser);
        if (Objects.nonNull(concerns) && concerns.size() > 0)
            throw new ConcernException("您已关注该用户");
        Concern concern = new Concern();
        concern.setToUser(toUser);
        concern.setDate(new Date());
        concern.setUser(user);
        concernRepository.save(concern);
        //关注用户的关注人数增加
        BoundHashOperations<String, String, Long> userOperations = redisTemplate
                .boundHashOps(ContractHelper.userFlag + user.getId());
        userOperations.putIfAbsent("userAmount", 0L);
        synchronized (userOperations.get("userAmount")) {
            Long userAmount = userOperations.get("userAmount");
            userOperations.put("userAmount", userAmount + 1L);
        }

        //被关注用户的粉丝人数增加
        BoundHashOperations<String, String, Long> toUserOperations = redisTemplate
                .boundHashOps(ContractHelper.userFlag + toUser.getId());
        toUserOperations.putIfAbsent("fansAmount", 0L);
        synchronized (toUserOperations.get("fansAmount")) {
            Long userAmount = toUserOperations.get("fansAmount");
            toUserOperations.put("fansAmount", userAmount + 1L);
        }

    }

    @Override
    public void leaveUser(Long id) throws ConcernException, LogException, IOException {
        User user = UserHelper.getUser();
        User toUser = userRepository.getOne(id);
        List<Concern> concerns = concernRepository.findByUserAndToUser(user, toUser);
        if (Objects.isNull(concerns) || concerns.size() == 0)
            throw new ConcernException("您已经取消关注该用户");
        for (Concern concern : concerns) {
            concernRepository.delete(concern);
        }

        //关注用户的关注人数减少
        BoundHashOperations<String, String, Long> userOperations = redisTemplate
                .boundHashOps(ContractHelper.userFlag + user.getId());
        synchronized (userOperations.get("userAmount")) {
            Long userAmount = userOperations.get("userAmount");
            userOperations.put("userAmount", userAmount - concerns.size());
        }

        //被关注用户的粉丝人数减少
        BoundHashOperations<String, String, Long> toUserOperations = redisTemplate
                .boundHashOps(ContractHelper.userFlag + toUser.getId());
        toUserOperations.putIfAbsent("fansAmount", 0L);
        synchronized (toUserOperations.get("fansAmount")) {
            Long fansAmount = toUserOperations.get("fansAmount");
            toUserOperations.put("fansAmount", fansAmount - concerns.size());
        }
    }

    @Override
    public AppUserConcermListModel changeModel(Concern concern) throws IOException {
        AppUserConcermListModel model = new AppUserConcermListModel();
        User toUser = concern.getToUser();
        model.setPid(toUser.getId());
        model.setUserName(toUser.getNickName());
        model.setUserHeadUrl(toUser.getImgURL());
        model.setUserLevelName(toUser.getLevel().getName());
        BoundHashOperations<String, String, Long> toUserOperations = redisTemplate
                .boundHashOps(ContractHelper.userFlag + toUser.getId());
        toUserOperations.putIfAbsent("fansAmount", 0L);
        toUserOperations.putIfAbsent("articleAmount", 0L);
        model.setArticleAmount(toUserOperations.get("articleAmount"));
        model.setFansAmount(toUserOperations.get("fansAmount"));

        return model;
    }

    @Override
    public AppUserConcermListModel[] changeModelList(List<Concern> list) throws IOException {
        AppUserConcermListModel[] models = new AppUserConcermListModel[list.size()];
        for (int i = 0; i < list.size(); i++) {
            models[i] = changeModel(list.get(i));
        }
        return models;
    }
}
