/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.converter;

import com.huotu.huobanplus.sns.model.common.ReportTargetType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by jin on 2016/11/4.
 */
@Component
public class ReportTargetTypeConverter implements Converter<String, ReportTargetType> {

    @Override
    public ReportTargetType convert(String source) {
        Assert.notNull(source, "source can not be null");
        return ReportTargetType.getType(Integer.parseInt(source));
    }
}
