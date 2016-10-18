/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service.impl;

import com.huotu.huobanplus.sns.entity.Report;
import com.huotu.huobanplus.sns.entity.User;
import com.huotu.huobanplus.sns.exception.LogException;
import com.huotu.huobanplus.sns.model.common.ReportTargetType;
import com.huotu.huobanplus.sns.repository.ReportRepository;
import com.huotu.huobanplus.sns.service.ReportService;
import com.huotu.huobanplus.sns.utils.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * Created by jin on 2016/10/18.
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Override
    public void report(ReportTargetType type, Long id, String note) throws IOException, LogException {
        Report report = new Report();
        report.setContent(note);
        User user = UserHelper.getUser();
        report.setReportTargetType(type);
        report.setUser(user);
        report.setTargetId(id);
        report.setDate(new Date());
        reportRepository.save(report);
    }
}
