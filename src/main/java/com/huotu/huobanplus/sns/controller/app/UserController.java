/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.controller.app;

import com.huotu.common.api.ApiResult;
import com.huotu.common.api.Output;
import com.huotu.huobanplus.sns.model.AppCircleArticleModel;
import com.huotu.huobanplus.sns.model.AppUserConcermListModel;
import com.huotu.huobanplus.sns.model.common.ReportTargetType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户模块
 * Created by Administrator on 2016/9/28.
 */
@RequestMapping("/app/user")
public interface UserController {

    /**
     * 关注圈子
     *
     * @param id 圈子Id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/concern", method = RequestMethod.GET)
    ApiResult concern(@RequestParam(value = "id") Long id) throws Exception;

    /**
     * 取消关注圈子
     *
     * @param id 圈子id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/cancelConcern", method = RequestMethod.GET)
    ApiResult cancelConcern(@RequestParam(value = "id") Long id) throws Exception;

    /**
     * 关注用户
     *
     * @param id 用户Id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/concernUser", method = RequestMethod.GET)
    ApiResult concernUser(@RequestParam(value = "id") Long id) throws Exception;

    /**
     * 取消关注用户
     *
     * @param id 用户Id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/cancelUser", method = RequestMethod.GET)
    ApiResult cancelUser(@RequestParam(value = "id") Long id) throws Exception;

    /**
     * 发布一篇文章
     * 注意：请对标题和内容进行敏感词校验
     *
     * @param id
     * @param name       文章标题
     * @param content    文章内容
     * @param pictureUrl 文章图片地址
     * @param circleId   所属圈子id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/publishArticle", method = RequestMethod.GET)
    ApiResult publishArticle(@RequestParam(value = "id") Long id
            , @RequestParam(value = "name") String name
            , @RequestParam(value = "content") String content
            , @RequestParam(value = "pictureUrl",required = false) String pictureUrl
            , @RequestParam(value = "circleId") Long circleId) throws Exception;


    /**
     * 评论文章
     * 注意：请对内容进行敏感词校验
     *
     * @param id      文章Id
     * @param content 评论内容
     * @return
     */
    @RequestMapping(value = "/commentArticle", method = RequestMethod.GET)
    ApiResult commentArticle(@RequestParam(value = "id") Long id
            , @RequestParam(value = "content") String content) throws Exception;


    /**
     * 举报
     *
     * @param type 类型
     * @param id   类型对应 id
     * @param note 说明
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/report", method = RequestMethod.GET)
    ApiResult report(@RequestParam(value = "type") ReportTargetType type
            , @RequestParam(value = "id") Long id
            , @RequestParam(value = "note") String note) throws Exception;

    /**
     * 我的关注
     * todo 暂定从redis获取数据
     *
     * @param lastId 上一个id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/myConcern", method = RequestMethod.GET)
    ApiResult myConcern(Output<AppUserConcermListModel[]> list
            , @RequestParam(value = "lastId",required = false) Long lastId) throws Exception;

    /**
     * 我的粉丝
     *
     * @param list
     * @param lastId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/myConcerned", method = RequestMethod.GET)
    ApiResult myConcerned(Output<AppUserConcermListModel[]> list
            , @RequestParam(value = "lastId",required = false) Long lastId) throws Exception;


    /**
     * 关注首页
     *
     * @param articleList 文章列表
     * @param lastId      上一个文章id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "concernIndex", method = RequestMethod.GET)
    ApiResult concernIndex(Output<AppCircleArticleModel[]> articleList
            , @RequestParam(value = "lastId",required = false) Long lastId) throws Exception;
}
