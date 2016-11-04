package com.huotu.huobanplus.sns.controller.impl.app;

import com.huotu.common.api.ApiResult;
import com.huotu.common.api.Output;
import com.huotu.huobanplus.sns.boot.PublicParameterHolder;
import com.huotu.huobanplus.sns.controller.app.CircleController;
import com.huotu.huobanplus.sns.entity.Circle;
import com.huotu.huobanplus.sns.entity.Slide;
import com.huotu.huobanplus.sns.model.*;
import com.huotu.huobanplus.sns.model.admin.CircleSearchModel;
import com.huotu.huobanplus.sns.model.common.AppCode;
import com.huotu.huobanplus.sns.repository.UserCircleRepository;
import com.huotu.huobanplus.sns.service.CircleService;
import com.huotu.huobanplus.sns.service.SlideService;
import com.huotu.huobanplus.sns.service.UserCircleService;
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
    private UserCircleService userCircleService;

    @Autowired
    private UserCircleRepository userCircleRepository;
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
        AppCircleIndexSuggestModel[] circleModels= circleService.getCircleAppModel(circles);
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

        AppCircleIndexSuggestModel[] circleModels= circleService.getCircleAppModel(circles);

        suggestList.outputData(circleModels);

        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult introduce(Output<AppCircleIntroduceModel> data, Long id) throws Exception {
        return null;
    }

    @Override
    public ApiResult top(Output<AppCircleModel> data, Output<AppCircleNoticeModel[]> noticeList, Output<AppCircleArticleModel> top, Long id) throws Exception {
        return null;
    }

    @Override
    public ApiResult list(Output<AppCircleArticleModel[]> articleList, Long id, Integer type, Long lastId) throws Exception {
        return null;
    }

    @Override
    public ApiResult article(Output<AppCircleArticleDetailModel> data, Output<AppCircleArticleCommentsModel[]> commentsList, Long id) throws Exception {
        return null;
    }

    @Override
    public ApiResult articleCommentsTop(Output<AppCircleArticleDetailModel> articleData, Output<AppClickUserListModel[]> userList, Long id) throws Exception {
        return null;
    }

    @Override
    public ApiResult articleCommentsList(Output<AppCircleArticleCommentsModel[]> commentsList, Long id, Long lastId) throws Exception {
        return null;
    }
}
