package com.huotu.huobanplus.sns.controller.impl.app;

import com.huotu.common.api.ApiResult;
import com.huotu.common.api.Output;
import com.huotu.huobanplus.sns.controller.app.WebController;
import com.huotu.huobanplus.sns.model.*;
import com.huotu.huobanplus.sns.model.common.AppCode;
import com.huotu.huobanplus.sns.service.ArticleService;
import com.huotu.huobanplus.sns.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by Administrator on 2016/10/8.
 */
@Controller
public class WebControllerImpl implements WebController {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;

    @Override
    public ApiResult user(Output<AppUserModel> data, Long id) throws Exception {
        data.outputData(userService.getAppUser(id));
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult userArticleList(Output<AppCircleArticleModel[]> articleList, Long id, Long lastId) throws Exception {
        List<AppCircleArticleModel> appCircleArticleModels = articleService.getUserArticleList(id, lastId);
        articleList.outputData(appCircleArticleModels.toArray(new AppCircleArticleModel[appCircleArticleModels.size()]));
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult search(Output<AppUserConcermListModel[]> userList, Output<AppCircleIndexListModel[]> circleList, Output<AppCircleIndexArticleListModel[]> articleList, Integer type, String key, Long lastId) throws Exception {


        return null;
    }
}
