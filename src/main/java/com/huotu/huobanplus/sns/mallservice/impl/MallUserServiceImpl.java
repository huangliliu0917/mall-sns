package com.huotu.huobanplus.sns.mallservice.impl;

import com.huotu.huobanplus.sns.mallentity.MallUser;
import com.huotu.huobanplus.sns.mallentity.MallUserBinding;
import com.huotu.huobanplus.sns.mallentity.MallUserLevel;
import com.huotu.huobanplus.sns.mallrepository.MallUserLevelRepository;
import com.huotu.huobanplus.sns.mallrepository.MallUserRepository;
import com.huotu.huobanplus.sns.mallservice.MallUserService;
import com.huotu.huobanplus.sns.model.common.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/31.
 */
@Service
public class MallUserServiceImpl implements MallUserService {

    @Autowired
    private MallUserRepository mallUserRepository;

    @Autowired
    private MallUserLevelRepository mallUserLevelRepository;

    @Override
    public Long userRegister(Long customerId, String phone
            , String openId
            , String nickName
            , String imageUrl) throws UnsupportedEncodingException {

        MallUser mallUser = new MallUser();
        mallUser.setCustomerId(customerId);
        mallUser.setMobile(phone);
        mallUser.setMobileBindRequired(false);
        mallUser.setWeixinImageUrl(imageUrl);
        mallUser.setWxNickName(nickName);
        mallUser.setBelongOne(0L);
        mallUser.setBelongTwo(0L);
        mallUser.setBelongThree(0L);
        mallUser.setRegTime(new Date());
        mallUser.setUserType(UserType.normal);
        mallUser.setUserBalance(0D);
        mallUser.setUserIntegral(0L);
        mallUser.setUserTempIntegral(0);
        mallUser.setLoginName(phone);
        String password = phone.substring(phone.length() - 6, 6);
        mallUser.setPassword(DigestUtils.md5DigestAsHex(password.getBytes("utf-8")));
//        mallUser.setRealName();
//        mallUser.setNickName();

        MallUserLevel mallUserLevel = mallUserLevelRepository.findByCustomerIdAndTypeMin(customerId, UserType.normal);
        if (mallUserLevel != null) mallUser.setLevelId(mallUserLevel.getId());

        MallUserBinding mallUserBinding = new MallUserBinding();
        mallUserBinding.setOpenId(openId);
        mallUser.setBinding(mallUserBinding);
        mallUser = mallUserRepository.save(mallUser);
        return mallUser.getId();
    }
}
