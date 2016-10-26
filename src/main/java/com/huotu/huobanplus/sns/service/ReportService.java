/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service;

import com.huotu.huobanplus.sns.entity.Report;
import com.huotu.huobanplus.sns.exception.LogException;
import com.huotu.huobanplus.sns.model.admin.ReportListModel;
import com.huotu.huobanplus.sns.model.admin.ReportSearchModel;
import com.huotu.huobanplus.sns.model.common.ReportTargetType;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

/**
 * Created by jin on 2016/10/18.
 */
public interface ReportService {

    /**
     * 举报
     *
     * @param type 举报类型
     * @param id   举报的id，针对用户，文章，评论等
     * @param note 举报理由
     * @throws IOException
     * @throws LogException
     */
    void report(ReportTargetType type, Long id, String note) throws IOException, LogException;


    /**
     * 举报列表
     * @param reportSearchModel
     * @return
     * @throws IOException
     */
    Page<Report> findReportList(ReportSearchModel reportSearchModel) throws IOException;

    /**
     * 将举报实体转换为举报列表model
     * @param reports
     * @return
     */
    List<ReportListModel> getReportListModelList(List<Report> reports);
}
