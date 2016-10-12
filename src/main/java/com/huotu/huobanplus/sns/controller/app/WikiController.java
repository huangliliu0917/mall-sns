package com.huotu.huobanplus.sns.controller.app;

import com.huotu.common.api.ApiResult;
import com.huotu.common.api.Output;
import com.huotu.huobanplus.sns.model.AppCategoryModel;
import com.huotu.huobanplus.sns.model.AppWikiListModel;
import com.huotu.huobanplus.sns.model.AppWikiModel;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @RequestMapping("/getCatalogList")
    ApiResult getCatalogList(Output<AppCategoryModel[]> catalogList, Integer id);

    /**
     * 百科列表
     *
     * @param wikilist  百科列表
     * @param catalogId 分类Id 值为空或0 显示全部
     * @param lastId    上一个id 值为空或0 显示第一页
     * @return
     * @throws Exception
     */
    @RequestMapping("/wikiList")
    ApiResult wikiList(Output<AppWikiListModel[]> wikilist, Integer catalogId, Long lastId) throws Exception;


    /**
     * 百科详情
     *
     * @param data 百科数据
     * @param id   百科id
     * @return
     * @throws Exception
     */
    @RequestMapping("/wiki")
    ApiResult wiki(Output<AppWikiModel> data, Long id) throws Exception;
}
