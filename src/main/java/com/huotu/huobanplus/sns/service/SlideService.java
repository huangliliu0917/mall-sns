/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service;

import com.huotu.huobanplus.sns.entity.Slide;

import java.io.IOException;
import java.util.List;

/**
 * banner文章
 * Created by slt on 2016/10/12.
 */
public interface SlideService {

    /**
     * 根据商户ID，查询圈子banner列表
     * @param customerId    商户ID
     * @return              banner列表
     * @throws IOException
     */
    List<Slide> findSlideList(Long customerId) throws IOException;


}
