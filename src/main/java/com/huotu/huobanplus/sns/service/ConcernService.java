/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service;

import com.huotu.huobanplus.sns.exception.ConcernException;
import com.huotu.huobanplus.sns.exception.LogException;

import java.io.IOException;

/**
 * Created by jin on 2016/10/10.
 */
public interface ConcernService {

    /**
     * 关注用户
     *
     * @param id 被关注用户id
     * @throws ConcernException 关注异常
     * @throws LogException     登录异常
     * @throws IOException      读写异常
     */
    void concernUser(Long id) throws ConcernException, LogException, IOException;

    /**
     * 取消关注用户
     *
     * @param id 被关注用户id
     * @throws ConcernException 关注异常
     * @throws LogException     登录异常
     * @throws IOException      读写异常
     */
    void leaveUser(Long id) throws ConcernException, LogException, IOException;
}
