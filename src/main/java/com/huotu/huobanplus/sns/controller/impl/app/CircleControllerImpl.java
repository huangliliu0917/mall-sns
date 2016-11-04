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
import com.huotu.huobanplus.sns.service.CircleService;
import com.huotu.huobanplus.sns.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/8.
 */
@Controller
public class CircleControllerImpl implements CircleController {
    @Autowired
    private SlideService slideService;

    @Autowired
    private CircleService circleService;
    @Override
    public ApiResult circleIndexTop(Output<AppCircleIndexSlideModel[]> slideList, Output<AppCircleIndexSuggestModel[]> suggestList) throws Exception {
        Long customerId=PublicParameterHolder.getParameters().getCustomerId();
        if(customerId==null){
            ApiResult.resultWith(AppCode.NOCUSTOMERID_ERROR);
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

//        String pictureUrl = "http://h.hiphotos.baidu.com/baike/w%3D730/sign=c5dfb6dd0124ab18e016e33405fbe69a/8b82b9014a90f603e434cfbc3112b31bb151ed41.jpg";
//        List<AppCircleIndexSlideModel> appCircleIndexSlideModelList = new ArrayList<>();
//        appCircleIndexSlideModelList.add(new AppCircleIndexSlideModel(pictureUrl, "http://www.baidu.com"));
//        appCircleIndexSlideModelList.add(new AppCircleIndexSlideModel(pictureUrl, "http://www.sina.com"));
//        slideList.outputData(appCircleIndexSlideModelList.toArray(new AppCircleIndexSlideModel[appCircleIndexSlideModelList.size()]));
//
//        List<AppCircleIndexSuggestModel> appCircleIndexSuggestModels = new ArrayList<>();
//        appCircleIndexSuggestModels.add(new AppCircleIndexSuggestModel("肠胃健康", pictureUrl, "http://baidu.com", 1012L));
//        appCircleIndexSuggestModels.add(new AppCircleIndexSuggestModel("母婴营养", pictureUrl, "http://baidu.com", 2012L));
//        appCircleIndexSuggestModels.add(new AppCircleIndexSuggestModel("品茶生活", pictureUrl, "http://baidu.com", 3012L));
//        suggestList.outputData(appCircleIndexSuggestModels.toArray(new AppCircleIndexSuggestModel[appCircleIndexSuggestModels.size()]));

        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult circleIndexSuggestList(Output<AppCircleIndexSuggestModel[]> suggestList,Integer pageNo,Integer pageSize) throws Exception {
        Long customerId=PublicParameterHolder.getParameters().getCustomerId();
        if(customerId==null){
            ApiResult.resultWith(AppCode.NOCUSTOMERID_ERROR);
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
    public ApiResult circleIndexList(Output<AppCircleIndexListModel[]> circlelist, Long lastId) throws Exception {
        String pictureUrl = "http://h.hiphotos.baidu.com/baike/w%3D730/sign=c5dfb6dd0124ab18e016e33405fbe69a/8b82b9014a90f603e434cfbc3112b31bb151ed41.jpg";

        List<AppCircleIndexListModel> appCircleIndexListModels = new ArrayList<>();
        AppCircleIndexListModel appCircleIndexListModel = new AppCircleIndexListModel();
        appCircleIndexListModel.setName("春节欢喜");
        appCircleIndexListModel.setNum(100L);
        appCircleIndexListModel.setPid(1L);
        appCircleIndexListModel.setPictureUrl(pictureUrl);
        appCircleIndexListModel.setUrl("http://baidu.com");
        List<AppCircleIndexArticleListModel> appCircleIndexArticleListModels = new ArrayList<>();
        AppCircleIndexArticleListModel appCircleIndexArticleListModel = new AppCircleIndexArticleListModel();
        appCircleIndexArticleListModel.setName("春天来了，春天真美丽，噢噢噢噢");
        appCircleIndexArticleListModel.setUserName("王强");
        appCircleIndexArticleListModel.setViewAmount(1210L);
        appCircleIndexArticleListModel.setCommentsAmount(100L);
        appCircleIndexArticleListModel.setUrl("http://baidu.com");
        appCircleIndexArticleListModels.add(appCircleIndexArticleListModel);
        appCircleIndexArticleListModel = new AppCircleIndexArticleListModel();
        appCircleIndexArticleListModel.setName("夏天来了，春天真美丽，噢噢噢噢");
        appCircleIndexArticleListModel.setUserName("王谁");
        appCircleIndexArticleListModel.setViewAmount(1210L);
        appCircleIndexArticleListModel.setCommentsAmount(100L);
        appCircleIndexArticleListModel.setUrl("http://baidu.com");
        appCircleIndexArticleListModels.add(appCircleIndexArticleListModel);
        appCircleIndexListModel.setList(appCircleIndexArticleListModels);
        appCircleIndexListModels.add(appCircleIndexListModel);

        for (int i = 0; i < 30; i++) {
            appCircleIndexListModel = new AppCircleIndexListModel();
            appCircleIndexListModel.setName("天下同情" + i);
            appCircleIndexListModel.setNum(100L);
            appCircleIndexListModel.setPid(1L);
            appCircleIndexListModel.setPictureUrl(pictureUrl);
            appCircleIndexListModel.setUrl("http://baidu.com");
            appCircleIndexArticleListModels = new ArrayList<>();
            appCircleIndexArticleListModel = new AppCircleIndexArticleListModel();
            appCircleIndexArticleListModel.setName("春天来了，春天真美丽，噢噢噢噢");
            appCircleIndexArticleListModel.setUserName("王强");
            appCircleIndexArticleListModel.setViewAmount(1210L);
            appCircleIndexArticleListModel.setCommentsAmount(100L);
            appCircleIndexArticleListModel.setUrl("http://baidu.com");
            appCircleIndexArticleListModels.add(appCircleIndexArticleListModel);
            appCircleIndexArticleListModel = new AppCircleIndexArticleListModel();
            appCircleIndexArticleListModel.setName("夏天来了，春天真美丽，噢噢噢噢");
            appCircleIndexArticleListModel.setUserName("王谁");
            appCircleIndexArticleListModel.setViewAmount(1210L);
            appCircleIndexArticleListModel.setCommentsAmount(100L);
            appCircleIndexArticleListModel.setUrl("http://baidu.com");
            appCircleIndexArticleListModels.add(appCircleIndexArticleListModel);
            appCircleIndexListModel.setList(appCircleIndexArticleListModels);
            appCircleIndexListModels.add(appCircleIndexListModel);
        }
        circlelist.outputData(appCircleIndexListModels.toArray(new AppCircleIndexListModel[appCircleIndexListModels.size()]));
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
