/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service.impl;

import com.huotu.huobanplus.sns.entity.Slide;
import com.huotu.huobanplus.sns.model.AppCircleIndexSlideModel;
import com.huotu.huobanplus.sns.repository.SlideRepository;
import com.huotu.huobanplus.sns.service.CommonConfigService;
import com.huotu.huobanplus.sns.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 圈子banner服务
 * Created by slt on 2016/10/12.
 */
@Service
public class SlideServiceImpl implements SlideService {
    @Autowired
    private SlideRepository slideRepository;
    @Autowired
    private CommonConfigService commonConfigService;
    @Override
    public List<Slide> findSlideList(Long customerId, Pageable pageable) throws IOException {
        if(pageable!=null){
            return slideRepository.findByCustomerId(customerId,pageable);
        }else {
            return slideRepository.findByCustomerIdOrderByIdDesc(customerId);
        }

    }

    @Override
    public AppCircleIndexSlideModel[] getSlideModelList(List<Slide> slides) {
        AppCircleIndexSlideModel[] models=new AppCircleIndexSlideModel[slides.size()];
        for(int i=0,size=slides.size();i<size;i++){
            models[i]=getSlideModel(slides.get(i));
        }
        return models;
    }

    @Override
    public AppCircleIndexSlideModel getSlideModel(Slide slide) {
        String pictureUrl=slide.getPictureUrl();
        String skipUrl=slide.getUrl();
        return new AppCircleIndexSlideModel(pictureUrl,skipUrl);
    }

}
