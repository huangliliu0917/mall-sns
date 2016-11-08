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
import com.huotu.huobanplus.sns.model.AppCircleNoticeDetailModel;
import com.huotu.huobanplus.sns.model.AppCircleNoticeModel;
import com.huotu.huobanplus.sns.repository.NoticeRepository;
import com.huotu.huobanplus.sns.service.CommonConfigService;
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

    @Autowired
    private CommonConfigService commonConfigService;

    @Override
    public AppCircleNoticeModel[] getNoticeModels(Long circleId) throws IOException {
        List<Notice> notices=noticeRepository.findByCircle_IdAndEnabledOrderByIdDesc(circleId,true);
        AppCircleNoticeModel[] models=new AppCircleNoticeModel[notices.size()];
        for(int i=0,size=notices.size();i<size;i++){
            AppCircleNoticeModel model=new AppCircleNoticeModel();
            Notice notice=notices.get(i);
            model.setName(notice.getName());
            model.setUrl(commonConfigService.getWebUrl()+"/app/circle/notice?id="+notice.getId());//todo 公告详情页面
            models[i]=model;
        }
        return models;
    }

    @Override
    public AppCircleNoticeDetailModel getAppNoticeDetailModel(Notice notice) {
        AppCircleNoticeDetailModel model=new AppCircleNoticeDetailModel();
        model.setPid(notice.getId());
        model.setName(notice.getName());
        model.setContent(notice.getContent());
        model.setTime(notice.getDate().getTime());
        model.setUserName(notice.getPublisher().getNickName());
        model.setUserHeadUrl(notice.getPublisher().getImgURL());
        model.setPictureUrl(notice.getPictureUrl());
        model.setClickAmount(notice.getClick());
        return model;
    }
}
