/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service;

import com.huotu.huobanplus.sns.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/10/9.
 */
public interface UserService {


    /**
     * 获取用户Id
     * 从加密的cookie中取出数据
     *
     * @param request
     * @return
     */
    Long getUserId(HttpServletRequest request) throws Exception;

    /**
     * 对用户的id进行加密
     * 放入cookie中
     */
    void setUserId(Long userId, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 根据条件查询用户列表
     *
     * @param nickName         用户昵称
     * @param authenticationId 身份id
     * @param levelId          等级id
     * @param pageable         分页
     * @return 用户列表
     * @throws IOException
     */
    Page<User> findByNickNameAndAuthenticationIdAndLevelId(String nickName,
                                                           Integer authenticationId,
                                                           Long levelId, Pageable pageable) throws IOException;
}
