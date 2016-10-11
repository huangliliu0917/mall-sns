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
import com.huotu.huobanplus.sns.repository.ConcernRepository;
import com.huotu.huobanplus.sns.repository.UserRepository;
import com.huotu.huobanplus.sns.service.ConcernService;
import com.huotu.huobanplus.sns.utils.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
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
//        redisTemplate
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
    }
}
