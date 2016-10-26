package com.huotu.huobanplus.sns.controller.app;

import com.huotu.common.api.ApiResult;
import com.huotu.common.api.Output;
import com.huotu.huobanplus.sns.model.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 圈子
 * Created by Administrator on 2016/9/29.
 */
@RequestMapping("/app/circle")
public interface CircleController {

    /**
     * 圈子首页幻灯及推荐
     *
     * @param slideList   幻灯轮播
     * @param suggestList 推荐
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/indexTop", method = RequestMethod.GET)
    ApiResult circleIndexTop(Output<AppCircleIndexSlideModel[]> slideList
            , Output<AppCircleIndexSuggestModel[]> suggestList) throws Exception;

    /**
     * 圈子推荐列表
     *
     * @param suggestList
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/circleIndexSuggestList", method = RequestMethod.GET)
    ApiResult circleIndexSuggestList(Output<AppCircleIndexSuggestModel[]> suggestList) throws Exception;

    /**
     * 圈子首页 关注的圈子列表
     * 按id进行倒序排列
     * <p>
     * 圈子的文章根据浏览量+时间取前2条
     *
     * @param circlelist 圈子列表
     * @param lastId     上一个id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/indexList", method = RequestMethod.GET)
    ApiResult circleIndexList(Output<AppCircleIndexListModel[]> circlelist
            , @RequestParam(value = "lastId",required = false) Long lastId) throws Exception;

    /**
     * 圈子介绍
     *
     * @param data 圈子介绍
     * @param id   圈子Id
     * @return
     * @throws Exception
     */
    @RequestMapping("/introduce")
    ApiResult introduce(Output<AppCircleIntroduceModel> data
            , @RequestParam("id") Long id) throws Exception;

    /**
     * 圈子公告及置顶
     *
     * @param data       圈子数据
     * @param noticeList 公告
     * @param top        置顶
     * @param id         圈子id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/top", method = RequestMethod.GET)
    ApiResult top(Output<AppCircleModel> data
            , Output<AppCircleNoticeModel[]> noticeList
            , Output<AppCircleArticleModel> top
            , @RequestParam("id") Long id) throws Exception;


    /**
     * 圈子文章列表
     *
     * @param articleList 文章列表
     * @param id          圈子id
     * @param type        0最新 1最热 (默认0)
     * @param lastId      上一个文章Id或热度(浏览量)
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    ApiResult list(Output<AppCircleArticleModel[]> articleList
            , @RequestParam("id") Long id
            , @RequestParam(value = "type" ,required = false) Integer type
            , @RequestParam(value = "lastId",required = false) Long lastId) throws Exception;

    /**
     * 圈子文章
     *
     * @param data         文章数据
     * @param commentsList 评论列表 (显示最新5条)
     * @param id           文章id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/article", method = RequestMethod.GET)
    ApiResult article(Output<AppCircleArticleDetailModel> data
            , Output<AppCircleArticleCommentsModel[]> commentsList
            , @RequestParam("id") Long id) throws Exception;


    /**
     * 文章评论
     *
     * @param articleData 文章数据
     * @param userList    评论用户列表
     * @param id          文章id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/articleCommentsTop", method = RequestMethod.GET)
    ApiResult articleCommentsTop(Output<AppCircleArticleDetailModel> articleData
            , Output<AppClickUserListModel[]> userList
            , @RequestParam("id") Long id) throws Exception;


    /**
     * 文章评论列表
     *
     * @param commentsList 文章评论列表
     * @param id           文章Id
     * @param lastId       上一个评论id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/articleCommentsList", method = RequestMethod.GET)
    ApiResult articleCommentsList(Output<AppCircleArticleCommentsModel[]> commentsList
            , @RequestParam("id") Long id
            , @RequestParam(value = "lastId",required = false) Long lastId) throws Exception;


}

