package com.huotu.huobanplus.sns.controller.impl.app;

import com.huotu.common.api.ApiResult;
import com.huotu.common.api.Output;
import com.huotu.huobanplus.sns.controller.app.UserController;
import com.huotu.huobanplus.sns.model.AppCircleArticleModel;
import com.huotu.huobanplus.sns.model.AppUserConcermListModel;
import com.huotu.huobanplus.sns.model.common.ReportTargetType;
import org.springframework.stereotype.Controller;

/**
 * Created by Administrator on 2016/9/28.
 */
@Controller
public class UserControllerImpl implements UserController{

    @Override
    public ApiResult concern(Long id) throws Exception {
        return null;
    }

    @Override
    public ApiResult cancelConcern(Long id) throws Exception {
        return null;
    }

    @Override
    public ApiResult concermUser(Long id) throws Exception {
        return null;
    }

    @Override
    public ApiResult leaveUser(Long id) throws Exception {
        return null;
    }

    @Override
    public ApiResult publishArticle(Long id, String name, String content, String pictureUrl, Long circleId) throws Exception {
        return null;
    }

    @Override
    public ApiResult commentArticle(Long id, String content) throws Exception {
        return null;
    }

    @Override
    public ApiResult report(ReportTargetType type, Long id, String note) throws Exception {
        return null;
    }

    @Override
    public ApiResult myConcerm(Output<AppUserConcermListModel[]> list, Long lastId) throws Exception {
        return null;
    }

    @Override
    public ApiResult myConcerned(Output<AppUserConcermListModel[]> list, Long lastId) throws Exception {
        return null;
    }

    @Override
    public ApiResult concernIndex(Output<AppCircleArticleModel[]> articleList, Long lastId) throws Exception {
        return null;
    }
}
