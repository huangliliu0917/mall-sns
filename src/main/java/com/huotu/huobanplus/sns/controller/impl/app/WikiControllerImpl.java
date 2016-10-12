package com.huotu.huobanplus.sns.controller.impl.app;

import com.huotu.common.api.ApiResult;
import com.huotu.common.api.Output;
import com.huotu.huobanplus.sns.controller.app.WikiController;
import com.huotu.huobanplus.sns.model.AppCategoryModel;
import com.huotu.huobanplus.sns.model.AppWikiListModel;
import com.huotu.huobanplus.sns.model.AppWikiModel;
import com.huotu.huobanplus.sns.model.common.AppCode;
import com.huotu.huobanplus.sns.service.ArticleService;
import com.huotu.huobanplus.sns.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
        List<AppCategoryModel> appCategoryModels = categoryService.getAppWikiCatalogList(id);
        catalogList.outputData(appCategoryModels.toArray(new AppCategoryModel[appCategoryModels.size()]));
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult wikiList(Output<AppWikiListModel[]> wikilist, Integer catalogId, Long lastId) throws Exception {
        List<AppWikiListModel> appWikiListModels = articleService.getAppWikiList(catalogId, lastId);
        wikilist.outputData(appWikiListModels.toArray(new AppWikiListModel[appWikiListModels.size()]));
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult wiki(Output<AppWikiModel> data, Long id) throws Exception {
        data.outputData(articleService.getAppWiki(id));
        return ApiResult.resultWith(AppCode.SUCCESS);
    }
}
