package com.huotu.huobanplus.sns.controller.impl.app;

import com.huotu.common.api.ApiResult;
import com.huotu.common.api.Output;
import com.huotu.huobanplus.sns.boot.PublicParameterHolder;
import com.huotu.huobanplus.sns.controller.app.CircleController;
import com.huotu.huobanplus.sns.entity.*;
import com.huotu.huobanplus.sns.model.*;
import com.huotu.huobanplus.sns.model.admin.CircleSearchModel;
import com.huotu.huobanplus.sns.model.common.AppCode;
import com.huotu.huobanplus.sns.repository.ArticleRepository;
import com.huotu.huobanplus.sns.repository.CircleRepository;
import com.huotu.huobanplus.sns.repository.NoticeRepository;
import com.huotu.huobanplus.sns.repository.UserCircleRepository;
import com.huotu.huobanplus.sns.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * 圈子Controller实现
 * Created by Administrator on 2016/10/8.
 */
@Controller
public class CircleControllerImpl implements CircleController {
    @Autowired
    private SlideService slideService;

    @Autowired
    private CircleService circleService;

    @Autowired
    private CircleRepository circleRepository;

    @Autowired
    private UserCircleService userCircleService;

    @Autowired
    private UserCircleRepository userCircleRepository;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    @Override
    public ApiResult circleIndexTop(Output<AppCircleIndexSlideModel[]> slideList, Output<AppCircleIndexSuggestModel[]> suggestList) throws Exception {
        Long customerId=PublicParameterHolder.getParameters().getCustomerId();
        if(customerId==null){
            return ApiResult.resultWith(AppCode.NOCUSTOMERID_ERROR);
        }
        List<Slide> slides=slideService.findSlideList(customerId,null);

        AppCircleIndexSlideModel[] slideModels= slideService.getSlideModelList(slides);
        slideList.outputData(slideModels);

        CircleSearchModel model=new CircleSearchModel();
        model.setPageNo(0);
        model.setPageSize(20);
        model.setCustomerId(customerId);
        model.setSuggested(1);
        model.setSortField("userAmount");
        model.setAscOrdesc(0);
        List<Circle> circles=circleService.findCircleList(model).getContent();
        AppCircleIndexSuggestModel[] circleModels= circleService.getCircleAppModels(circles);
        suggestList.outputData(circleModels);

        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult circleIndexSuggestList(Output<AppCircleIndexSuggestModel[]> suggestList,Integer pageNo,Integer pageSize) throws Exception {
        Long customerId=PublicParameterHolder.getParameters().getCustomerId();
        if(customerId==null){
            return ApiResult.resultWith(AppCode.NOCUSTOMERID_ERROR);
        }

        CircleSearchModel model=new CircleSearchModel();
        model.setPageNo(pageNo);
        model.setPageSize(pageSize);
        model.setCustomerId(customerId);
        model.setSortField("userAmount");
        model.setAscOrdesc(0);
        List<Circle> circles=circleService.findCircleList(model).getContent();

        AppCircleIndexSuggestModel[] circleModels= circleService.getCircleAppModels(circles);

        suggestList.outputData(circleModels);

        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult introduce(Output<AppCircleIntroduceModel> data, Long id) throws Exception {
        Circle circle=circleRepository.findOne(id);
        if(circle==null){
            return ApiResult.resultWith(AppCode.ERROR_NO_CIRCLE);
        }

        AppCircleIntroduceModel model=circleService.getCircleDetailsModel(circle);

        data.outputData(model);

        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult top(Output<AppCircleModel> data, Output<AppCircleNoticeModel[]> noticeList, Output<AppCircleArticleModel[]> top, Long id) throws Exception {
        Long customerId=PublicParameterHolder.getParameters().getCustomerId();
        if(customerId==null){
            return ApiResult.resultWith(AppCode.NOCUSTOMERID_ERROR);
        }

        User user=PublicParameterHolder.getParameters().getCurrentUser();
        if(user==null){
            return ApiResult.resultWith(AppCode.NOUSER_ERROR);
        }

        Circle circle=circleRepository.findOne(id);
        if(circle==null){
            return ApiResult.resultWith(AppCode.ERROR_NO_CIRCLE);
        }


        AppCircleModel appCircleModel=circleService.getAppCircleModel(circle);
        data.outputData(appCircleModel);

        AppCircleNoticeModel[] noticeModels=noticeService.getNoticeModels(circle.getId());
        noticeList.outputData(noticeModels);

        AppCircleArticleModel[] articleModels=articleService.getTopArticleModels(user.getId(),customerId,circle.getId());
        top.outputData(articleModels);

        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult list(Output<AppCircleArticleModel[]> articleList, Long id, Integer type, Long lastId) throws Exception {
        Long customerId=PublicParameterHolder.getParameters().getCustomerId();
        if(customerId==null){
            return ApiResult.resultWith(AppCode.NOCUSTOMERID_ERROR);
        }

        User user=PublicParameterHolder.getParameters().getCurrentUser();
        if(user==null){
            return ApiResult.resultWith(AppCode.NOUSER_ERROR);
        }

        Circle circle=circleRepository.findOne(id);
        if(circle==null){
            return ApiResult.resultWith(AppCode.ERROR_NO_CIRCLE);
        }
        type=type==null?0:type;

        AppCircleArticleModel[] articleModels=articleService.getArticleListModels(customerId,user.getId(),lastId,circle.getId(),type);

        articleList.outputData(articleModels);

        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult article(Output<AppCircleArticleDetailModel> data,
                             Long id) throws Exception {
        Article article=articleRepository.findOne(id);
        if(article==null){
            return ApiResult.resultWith(AppCode.ERROR_NO_ARTICLE);
        }
        AppCircleArticleDetailModel articleDetailModel=articleService.getArticleDetailModel(article);

        data.outputData(articleDetailModel);

        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult notice(Output<AppCircleNoticeDetailModel> data, Long id) throws Exception {
        Notice notice=noticeRepository.findOne(id);
        if(notice==null){
            return ApiResult.resultWith(AppCode.ERROR_NO_NOTICE);
        }
        AppCircleNoticeDetailModel noticeDetailModel=noticeService.getAppNoticeDetailModel(notice);

        data.outputData(noticeDetailModel);

        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult articleCommentsTop(Output<AppClickUserListModel[]> userList, Long id) throws Exception {
        return null;
    }

    @Override
    public ApiResult articleCommentsList(Output<AppCircleArticleCommentsModel[]> commentsList, Long id, Long lastId) throws Exception {
        return null;
    }
}
