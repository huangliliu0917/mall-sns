package com.huotu.huobanplus.sns.controller.impl.app;

import com.huotu.common.api.ApiResult;
import com.huotu.common.api.Output;
import com.huotu.huobanplus.sns.boot.PublicParameterHolder;
import com.huotu.huobanplus.sns.controller.app.WebController;
import com.huotu.huobanplus.sns.model.*;
import com.huotu.huobanplus.sns.model.common.AppCode;
import com.huotu.huobanplus.sns.service.ArticleService;
import com.huotu.huobanplus.sns.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Administrator on 2016/10/8.
 */
@Controller
public class WebControllerImpl implements WebController {

    private static Log log = LogFactory.getLog(WebControllerImpl.class);


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
    public ApiResult search(Output<AppCircleIndexArticleListModel[]> articleList, String key, Long lastId) throws Exception {
        List<AppCircleIndexArticleListModel> listModels = articleService.search(PublicParameterHolder.getParameters().getCustomerId(), key, lastId);
        articleList.outputData(listModels.toArray(new AppCircleIndexArticleListModel[listModels.size()]));
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

//    @Override
//    public ApiResult getWeixinInfo(Output<String> openId, Output<String> nickName, Output<String> imageUrl, @RequestParam("customerId") Long customerId) {
//        return null;
//    }


//    @Override
//    public ApiResult mobileLogin(Output<String> data, String userName, String password) {
//        data.outputData(appSecurityService.createJWT("websns", userName, 100000));
//        return ApiResult.resultWith(AppCode.SUCCESS);
//    }
//
//    @Override
//    public ApiResult checkToken(HttpServletRequest request) {
//        String jwt = request.getHeader("authentication");
//        String userName = appSecurityService.parseJWT(jwt);
//
//        log.info(userName);
//        return ApiResult.resultWith(AppCode.SUCCESS);
//    }


}
