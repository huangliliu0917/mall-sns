/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.controller.app;

import com.huotu.common.api.ApiResult;
import com.huotu.common.api.Output;
import com.huotu.huobanplus.sns.model.AppCategoryModel;
import com.huotu.huobanplus.sns.model.AppWikiListModel;
import com.huotu.huobanplus.sns.model.AppWikiModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 科普，百科
 * Created by Administrator on 2016/9/29.
 */
@RequestMapping("/app/wiki")
public interface WikiController {


    /**
     * 获得百科分类列表
     *
     * @param catalogList 分类列表
     * @param id          父分类Id，可以为空
     * @return
     */
    @RequestMapping(value = "/getCatalogList", method = RequestMethod.GET)
    ApiResult getCatalogList(Output<AppCategoryModel[]> catalogList
            , @RequestParam("id") Integer id);

    /**
     * 百科列表
     *
     * @param wikilist  百科列表
     * @param catalogId 分类Id 值为空或0 显示全部
     * @param lastId    上一个id 值为空或0 显示第一页
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/wikiList", method = RequestMethod.GET)
    ApiResult wikiList(Output<AppWikiListModel[]> wikilist
            , @RequestParam(value = "catalogId", required = false) Integer catalogId
            , @RequestParam(value = "lastId", required = false) Long lastId) throws Exception;


    /**
     * 百科详情
     *
     * @param data 百科数据
     * @param id   百科id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/wiki", method = RequestMethod.GET)
    ApiResult wiki(Output<AppWikiModel> data
            , @RequestParam("id") Long id) throws Exception;

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    ApiResult test(Output<Long> data
            , @RequestParam("id") Long id) throws Exception;
}
