/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.controller.impl.app;

import com.huotu.common.api.ApiResult;
import com.huotu.common.api.Output;
import com.huotu.huobanplus.sns.boot.PublicParameterHolder;
import com.huotu.huobanplus.sns.controller.app.WikiController;
import com.huotu.huobanplus.sns.model.AppCategoryModel;
import com.huotu.huobanplus.sns.model.AppWikiListModel;
import com.huotu.huobanplus.sns.model.AppWikiModel;
import com.huotu.huobanplus.sns.model.common.AppCode;
import com.huotu.huobanplus.sns.service.ArticleService;
import com.huotu.huobanplus.sns.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Administrator on 2016/10/8.
 */
@Controller
public class WikiControllerImpl implements WikiController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoryService categoryService;


    @Override
    public ApiResult getCatalogList(Output<AppCategoryModel[]> catalogList, Integer id) {
        Long customerId = PublicParameterHolder.getParameters().getCustomerId();
        List<AppCategoryModel> appCategoryModels = categoryService.getAppWikiCatalogList(customerId, id);
        catalogList.outputData(appCategoryModels.toArray(new AppCategoryModel[appCategoryModels.size()]));
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult wikiList(Output<AppWikiListModel[]> wikilist, Integer catalogId, Long lastId) throws Exception {
        List<AppWikiListModel> appWikiListModels = articleService.getAppWikiList(PublicParameterHolder.getParameters().getCustomerId(), catalogId, lastId);
        wikilist.outputData(appWikiListModels.toArray(new AppWikiListModel[appWikiListModels.size()]));
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult wiki(Output<AppWikiModel> data, Long id) throws Exception {
        data.outputData(articleService.getAppWiki(id));
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult test(Output<Long> data, @RequestParam("id") Long id) throws Exception {
        data.outputData(1L);
        return ApiResult.resultWith(AppCode.SUCCESS);
    }
}
