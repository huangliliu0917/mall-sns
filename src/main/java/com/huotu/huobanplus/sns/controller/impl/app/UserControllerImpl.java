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
import com.huotu.huobanplus.sns.controller.app.UserController;
import com.huotu.huobanplus.sns.entity.*;
import com.huotu.huobanplus.sns.exception.ClickException;
import com.huotu.huobanplus.sns.exception.ConcernException;
import com.huotu.huobanplus.sns.exception.ContentException;
import com.huotu.huobanplus.sns.exception.NeedLoginException;
import com.huotu.huobanplus.sns.model.AppCircleArticleModel;
import com.huotu.huobanplus.sns.model.AppCircleIndexListModel;
import com.huotu.huobanplus.sns.model.AppUserConcermListModel;
import com.huotu.huobanplus.sns.model.common.AppCode;
import com.huotu.huobanplus.sns.model.common.ArticleType;
import com.huotu.huobanplus.sns.model.common.ReportTargetType;
import com.huotu.huobanplus.sns.repository.ArticleCommentRepository;
import com.huotu.huobanplus.sns.repository.ArticleRepository;
import com.huotu.huobanplus.sns.repository.ConcernRepository;
import com.huotu.huobanplus.sns.repository.UserArticleRepository;
import com.huotu.huobanplus.sns.service.*;
import com.huotu.huobanplus.sns.utils.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Created by Administrator on 2016/9/28.
 */
@Controller
public class UserControllerImpl implements UserController {

    private static Random random = new Random();
    @Autowired
    private UserCircleService userCircleService;
    @Autowired
    private ConcernService concernService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private SensitiveService sensitiveService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ConcernRepository concernRepository;
    @Autowired
    private UserArticleRepository userArticleRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleCommentRepository articleCommentRepository;

    @Override
    public ApiResult concern(Long id) throws NeedLoginException, ConcernException, IOException {
        userCircleService.concern(id);
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult cancelConcern(Long id) throws NeedLoginException, IOException, ConcernException {
        userCircleService.cancelConcern(id);
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult concernUser(Long id) throws NeedLoginException, IOException, ConcernException {
        concernService.concernUser(id);
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult cancelUser(Long id) throws NeedLoginException, IOException, ConcernException {
        concernService.leaveUser(id);
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult publishArticle(Output<Long> data, Long id, String name, String content, String pictureUrl, Long circleId)
            throws ContentException, IOException, NeedLoginException, InterruptedException {
        if (!sensitiveService.ContainSensitiveWords(content)) {
            throw new ContentException(AppCode.ERROR_SENSITIVE_CONTENT.getValue(), AppCode.ERROR_SENSITIVE_CONTENT.getName());
        }
        User user = UserHelper.getUser();
        //文章内容截取成为简介,暂定为80个字
//        String summary;
//        if (content.length() < 80) {
//            summary = content;
//        } else {
//            summary = content.substring(0, 80) + "...";
//        }
//        //保存文章
//        Article article = articleService.save(ArticleType.Normal.getValue(), id, name, user.getId(),
//                pictureUrl, content, summary, null, circleId, null, null);
//        if (Objects.isNull(id)) {
//            //新增文章用户关联
//            articleService.addArticleResult(ArticleType.Normal.getValue(),
//                    name, user, pictureUrl, content, circleId);
//        }
        //新增文章用户关联
        Long articleId = articleService.addArticleResult(ArticleType.Normal.getValue(),
                name, user, pictureUrl, content, circleId);
        data.outputData(articleId);
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult commentArticle(Output<Long> data, Long id, String content)
            throws NeedLoginException, IOException, ContentException {
        if (!sensitiveService.ContainSensitiveWords(content)) {
            throw new ContentException(AppCode.ERROR_SENSITIVE_CONTENT.getValue(), AppCode.ERROR_SENSITIVE_CONTENT.getName());
        }
        User user = UserHelper.getUser();
        ArticleComment comment = articleService.commentArticle(id, content, user);
        data.outputData(comment.getId());
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult report(ReportTargetType type, Long id, String note) throws NeedLoginException, IOException {
        reportService.report(type, id, note);
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult myConcern(Output<AppUserConcermListModel[]> list, Long lastId) throws NeedLoginException, IOException {
        User user = UserHelper.getUser();
        List<Concern> concerns;
        if (Objects.isNull(lastId)) {
            concerns = concernRepository.findTop10ByUserOrderByIdDesc(user);
        } else {
            concerns = concernRepository.findTop10ByUserAndIdLessThanOrderByIdDesc(user, lastId);
        }
        AppUserConcermListModel[] models = concernService.changeModelList(concerns);
        list.outputData(models);
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult myConcerned(Output<AppUserConcermListModel[]> list, Long lastId) throws NeedLoginException, IOException {
        User user = UserHelper.getUser();
        List<Concern> concerns;
        if (Objects.isNull(lastId)) {
            concerns = concernRepository.findTop10ByToUserOrderByIdDesc(user);
        } else {
            concerns = concernRepository.findTop10ByToUserAndIdLessThanOrderByIdDesc(user, lastId);
        }
        AppUserConcermListModel[] models = concernService.changeModelList(concerns);
        list.outputData(models);
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult concernIndex(Output<AppCircleArticleModel[]> articleList, Long lastId) throws Exception {
        User user = UserHelper.getUser();
        List<UserArticle> articles;
        if (Objects.isNull(lastId)) {
            articles = userArticleRepository.findTop10ByOwnerIdOrderByIdDesc(user.getId());
        } else {
            articles = userArticleRepository.findTop10ByOwnerIdAndIdLessThanOrderByIdDesc(user.getId(), lastId);
        }
        if (Objects.nonNull(articles) && articles.size() > 0) {
            AppCircleArticleModel[] models = userService.changeModelArray(articles);
            articleList.outputData(models);
        } else {
            //从哪里找些文章，填满关注首页
            List<Concern> concerns = concernRepository.findByUser(user);
            if (Objects.isNull(lastId)) {
                int index = random.nextInt(concerns.size());
                List<Article> list = articleRepository.findTop10ByPublisherOrderByIdDesc(concerns.get(index).getToUser());
                AppCircleArticleModel[] models = userService.changeModelArrayByArticle(list);
                articleList.outputData(models);
            }
        }
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult articleClick(@RequestParam(value = "id") Long id) throws NeedLoginException, IOException, ClickException {
        User user = UserHelper.getUser();
        Article article = articleRepository.getOne(id);
        articleService.articleClick(article, user);
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult replyComment(@RequestParam(value = "id") Long id, @RequestParam(value = "content") String content)
            throws NeedLoginException, IOException, ContentException {
        if (!sensitiveService.ContainSensitiveWords(content)) {
            throw new ContentException(AppCode.ERROR_SENSITIVE_CONTENT.getValue(), AppCode.ERROR_SENSITIVE_CONTENT.getName());
        }
        ArticleComment articleComment = articleCommentRepository.getOne(id);
        User user = UserHelper.getUser();
        articleService.replyComment(articleComment, user, content);
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult circleIndexList(Output<AppCircleIndexListModel[]> circlelist, Long lastId) throws Exception {
        Long customerId= PublicParameterHolder.getParameters().getCustomerId();
        if(customerId==null){
            return ApiResult.resultWith(AppCode.NOCUSTOMERID_ERROR);
        }
        User user=PublicParameterHolder.getParameters().getCurrentUser();
        if(user==null){
            return ApiResult.resultWith(AppCode.NOUSER_ERROR);
        }

        List<UserCircle> list=userCircleService.getUserCircleList(customerId,user.getId(),lastId);

        AppCircleIndexListModel[] models=userCircleService.getCircleIndexListModelList(list);

        circlelist.outputData(models);

        return ApiResult.resultWith(AppCode.SUCCESS);
    }
}
