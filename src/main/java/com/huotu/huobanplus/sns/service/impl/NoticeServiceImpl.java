/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service.impl;

import com.huotu.huobanplus.sns.entity.Notice;
import com.huotu.huobanplus.sns.model.AppCircleNoticeModel;
import com.huotu.huobanplus.sns.repository.NoticeRepository;
import com.huotu.huobanplus.sns.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 公告服务实现
 * Created by slt on 2016/10/31.
 */
@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeRepository noticeRepository;

    @Override
    public AppCircleNoticeModel[] getNoticeModels(Long customerId) throws IOException {
        List<Notice> notices=noticeRepository.findByCustomerIdOrderByIdDesc(customerId);
        AppCircleNoticeModel[] models=new AppCircleNoticeModel[notices.size()];
        notices.forEach(notice -> {
            AppCircleNoticeModel model=new AppCircleNoticeModel();
            model.setName(notice.getName());
            model.setUrl(""+notice.getId());//todo 公告详情页面
        });

        return models;
    }
}
