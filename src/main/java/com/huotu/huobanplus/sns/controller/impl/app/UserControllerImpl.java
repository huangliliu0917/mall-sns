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
import com.huotu.huobanplus.sns.controller.app.UserController;
import com.huotu.huobanplus.sns.entity.Article;
import com.huotu.huobanplus.sns.entity.User;
import com.huotu.huobanplus.sns.exception.ConcernException;
import com.huotu.huobanplus.sns.exception.LogException;
import com.huotu.huobanplus.sns.model.AppCircleArticleModel;
import com.huotu.huobanplus.sns.model.AppUserConcermListModel;
import com.huotu.huobanplus.sns.model.common.ArticleType;
import com.huotu.huobanplus.sns.model.common.ReportTargetType;
import com.huotu.huobanplus.sns.service.*;
import com.huotu.huobanplus.sns.utils.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by Administrator on 2016/9/28.
 */
@Controller
public class UserControllerImpl implements UserController {

    @Autowired
    private UserCircleService userCircleService;

    @Autowired
    private ConcernService concernService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private SensitiveService sensitiveService;
    @Autowired
    private ArticleService articleService;

    @Override
    public ApiResult concern(Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            userCircleService.concern(id);
            apiResult.setResultCode(200);
            apiResult.setResultDescription("关注成功");
        } catch (ConcernException e) {
            apiResult.setResultCode(50001);
            apiResult.setResultDescription(e.getMessage());
            return apiResult;
        } catch (LogException e) {
            apiResult.setResultCode(50002);
            apiResult.setResultDescription(e.getMessage());
            return apiResult;
        } catch (IOException e) {
            apiResult.setResultCode(50003);
            apiResult.setResultDescription(e.getMessage());
            return apiResult;
        }
        return apiResult;
    }

    @Override
    public ApiResult cancelConcern(Long id) {
        ApiResult apiResult = new ApiResult();
        try {
            userCircleService.cancelConcern(id);
            apiResult.setResultCode(200);
            apiResult.setResultDescription("取消关注成功");
        } catch (ConcernException e) {
            apiResult.setResultCode(50001);
            apiResult.setResultDescription(e.getMessage());
            return apiResult;
        } catch (LogException e) {
            apiResult.setResultCode(50002);
            apiResult.setResultDescription(e.getMessage());
            return apiResult;
        } catch (IOException e) {
            apiResult.setResultCode(50003);
            apiResult.setResultDescription(e.getMessage());
            return apiResult;
        }
        return apiResult;
    }

    @Override
    public ApiResult concernUser(Long id) throws Exception {
        ApiResult apiResult = new ApiResult();
        try {
            concernService.concernUser(id);
            apiResult.setResultCode(200);
            apiResult.setResultDescription("关注成功");
        } catch (ConcernException e) {
            apiResult.setResultCode(50001);
            apiResult.setResultDescription(e.getMessage());
            return apiResult;
        } catch (LogException e) {
            apiResult.setResultCode(50002);
            apiResult.setResultDescription(e.getMessage());
            return apiResult;
        } catch (IOException e) {
            apiResult.setResultCode(50003);
            apiResult.setResultDescription(e.getMessage());
            return apiResult;
        }
        return apiResult;
    }

    @Override
    public ApiResult cancelUser(Long id) throws Exception {
        ApiResult apiResult = new ApiResult();
        try {
            concernService.leaveUser(id);
            apiResult.setResultCode(200);
            apiResult.setResultDescription("取消关注成功");
        } catch (ConcernException e) {
            apiResult.setResultCode(50001);
            apiResult.setResultDescription(e.getMessage());
            return apiResult;
        } catch (LogException e) {
            apiResult.setResultCode(50002);
            apiResult.setResultDescription(e.getMessage());
            return apiResult;
        } catch (IOException e) {
            apiResult.setResultCode(50003);
            apiResult.setResultDescription(e.getMessage());
            return apiResult;
        }
        return apiResult;
    }

    @Transactional
    @Override
    public ApiResult publishArticle(Long id, String name, String content, String pictureUrl, Long circleId) throws Exception {
        ApiResult apiResult = new ApiResult();
        if (sensitiveService.ContainSensitiveWords(content)) {
            apiResult.setResultCode(50001);
            apiResult.setResultDescription("您发表的内容包含敏感词汇");
            return apiResult;
        }
        User user = UserHelper.getUser();
        //文章内容截取成为简介,暂定为80个字
        String summary;
        if (content.length() < 80) {
            summary = content;
        } else {
            summary = content.substring(0, 80) + "...";
        }
        //保存文章
        Article article = articleService.save(ArticleType.Normal.getValue(), id, name, user.getId(),
                pictureUrl, content, summary, null, circleId, null, null);
        if (Objects.isNull(id)) {
            //新增文章用户关联
            articleService.addArticleResult(ArticleType.Normal.getValue(), article.getId(),
                    name, user, pictureUrl, summary, circleId);
        }
        apiResult.setResultCode(200);
        apiResult.setResultDescription("发表成功");
        return apiResult;
    }

    @Override
    public ApiResult commentArticle(Long id, String content) throws Exception {
        ApiResult apiResult = new ApiResult();
        if (sensitiveService.ContainSensitiveWords(content)) {
            apiResult.setResultCode(50001);
            apiResult.setResultDescription("您的评论包含敏感词汇");
            return apiResult;
        }
        User user = UserHelper.getUser();
        return null;
    }

    @Override
    public ApiResult report(ReportTargetType type, Long id, String note) throws Exception {
        ApiResult apiResult = new ApiResult();
        try {
            reportService.report(type, id, note);
            apiResult.setResultCode(200);
            apiResult.setResultDescription("举报成功");
        } catch (IOException e) {
            apiResult.setResultCode(50003);
            apiResult.setResultDescription(e.getMessage());
            return apiResult;
        } catch (LogException e) {
            apiResult.setResultCode(50002);
            apiResult.setResultDescription(e.getMessage());
            return apiResult;
        }
        return apiResult;
    }

    @Override
    public ApiResult myConcern(Output<AppUserConcermListModel[]> list, Long lastId) throws Exception {
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
