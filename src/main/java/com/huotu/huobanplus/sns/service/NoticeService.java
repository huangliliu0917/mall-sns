/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service;

import com.huotu.huobanplus.sns.model.AppCircleNoticeModel;

import java.io.IOException;

/**
 * 公告服务
 * Created by slt  on 2016/10/31.
 */
public interface NoticeService {
    /**
     * 根据圈子ID获取公告列表
     * @param circleId      圈子ID
     * @return
     * @throws IOException
     */
    AppCircleNoticeModel[] getNoticeModels(Long circleId) throws IOException;

}
