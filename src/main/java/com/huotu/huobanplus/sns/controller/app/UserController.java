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
import com.huotu.huobanplus.sns.exception.ClickException;
import com.huotu.huobanplus.sns.exception.ConcernException;
import com.huotu.huobanplus.sns.exception.ContentException;
import com.huotu.huobanplus.sns.exception.NeedLoginException;
import com.huotu.huobanplus.sns.model.AppCircleArticleModel;
import com.huotu.huobanplus.sns.model.AppCircleIndexListModel;
import com.huotu.huobanplus.sns.model.AppUserConcermListModel;
import com.huotu.huobanplus.sns.model.common.ReportTargetType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * 用户模块
 * Created by Administrator on 2016/9/28.
 */
@SuppressWarnings("unused")
@RequestMapping("/app/user")
public interface UserController {

    /**
     * 关注圈子
     *
     * @param id 圈子id
     * @return
     * @throws NeedLoginException 用户未登录
     * @throws ConcernException   30001 已经关注过该圈子
     * @throws IOException        读写数据异常
     */
    @RequestMapping(value = "/concern", method = RequestMethod.POST)
    ApiResult concern(@RequestParam(value = "id") Long id) throws NeedLoginException, ConcernException, IOException;

    /**
     * 取消关注圈子
     *
     * @param id 圈子id
     * @return
     * @throws NeedLoginException 用户未登录
     * @throws ConcernException   30002 未关注该圈子
     * @throws IOException        读写数据异常
     */
    @RequestMapping(value = "/cancelConcern", method = RequestMethod.POST)
    ApiResult cancelConcern(@RequestParam(value = "id") Long id) throws NeedLoginException, IOException, ConcernException;

    /**
     * 关注用户
     *
     * @param id 用户id
     * @return
     * @throws NeedLoginException 用户未登录
     * @throws IOException        读写数据异常
     * @throws ConcernException   30101 已关注该用户
     */
    @RequestMapping(value = "/concernUser", method = RequestMethod.POST)
    ApiResult concernUser(@RequestParam(value = "id") Long id) throws NeedLoginException, IOException, ConcernException;

    /**
     * 取消关注用户
     *
     * @param id 用户id
     * @return
     * @throws NeedLoginException 用户未登录
     * @throws IOException        读写数据异常
     * @throws ConcernException   30102 未关注该用户
     */
    @RequestMapping(value = "/cancelUser", method = RequestMethod.POST)
    ApiResult cancelUser(@RequestParam(value = "id") Long id) throws NeedLoginException, IOException, ConcernException;

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
     * @throws ContentException     30201 内容包含敏感词汇
     * @throws IOException          读写数据异常
     * @throws NeedLoginException   未登录
     * @throws InterruptedException 线程异常
     */
    @RequestMapping(value = "/publishArticle", method = RequestMethod.POST)
    ApiResult publishArticle(Output<Long> data
            , @RequestParam(value = "id", required = false) Long id
            , @RequestParam(value = "name") String name
            , @RequestParam(value = "content") String content
            , @RequestParam(value = "pictureUrl", required = false) String pictureUrl
            , @RequestParam(value = "circleId") Long circleId)
            throws ContentException, IOException, NeedLoginException, InterruptedException;


    /**
     * 评论文章
     * 注意：请对内容进行敏感词校验
     *
     * @param id      文章Id
     * @param content 评论内容
     * @return
     * @throws NeedLoginException 用户未登录
     * @throws IOException        读写数据异常
     * @throws ContentException   30201 评论包含敏感词汇
     */
    @RequestMapping(value = "/commentArticle", method = RequestMethod.POST)
    ApiResult commentArticle(Output<Long> data, @RequestParam(value = "id") Long id
            , @RequestParam(value = "content") String content) throws NeedLoginException, IOException, ContentException;


    /**
     * 举报
     *
     * @param type 类型
     * @param id   类型对应 id
     * @param note 说明
     * @return
     * @throws NeedLoginException 用户未登录
     * @throws IOException        读写数据异常
     */
    @RequestMapping(value = "/report", method = RequestMethod.POST)
    ApiResult report(@RequestParam(value = "type") ReportTargetType type
            , @RequestParam(value = "id") Long id
            , @RequestParam(value = "note") String note) throws NeedLoginException, IOException;

    /**
     * 我的关注
     * todo 暂定从redis获取数据
     *
     * @param list   关注列表
     * @param lastId 上一个id
     * @return
     * @throws NeedLoginException 用户未登录
     * @throws IOException        数据读写异常
     */
    @RequestMapping(value = "/myConcern", method = RequestMethod.GET)
    ApiResult myConcern(Output<AppUserConcermListModel[]> list
            , @RequestParam(value = "lastId", required = false) Long lastId) throws NeedLoginException, IOException;

    /**
     * 我的粉丝
     *
     * @param list   粉丝列表
     * @param lastId 上一个id
     * @return
     * @throws NeedLoginException 用户未登录
     * @throws IOException        数据读写异常
     */
    @RequestMapping(value = "/myConcerned", method = RequestMethod.GET)
    ApiResult myConcerned(Output<AppUserConcermListModel[]> list
            , @RequestParam(value = "lastId", required = false) Long lastId) throws NeedLoginException, IOException;


    /**
     * 关注首页
     *
     * @param articleList 文章列表
     * @param lastId      上一个文章id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/concernIndex", method = RequestMethod.GET)
    ApiResult concernIndex(Output<AppCircleArticleModel[]> articleList
            , @RequestParam(value = "lastId", required = false) Long lastId) throws Exception;

    /**
     * 文章点赞
     *
     * @param id 文章id
     * @return
     * @throws NeedLoginException 用户未登录
     * @throws IOException        读写数据异常
     * @throws ClickException     文章已点赞
     */
    @RequestMapping(value = "/articleClick", method = RequestMethod.POST)
    ApiResult articleClick(@RequestParam(value = "id") Long id) throws NeedLoginException, IOException, ClickException;

    /**
     * 回复评论
     *
     * @param id      评论id
     * @param content 评论内容
     * @return
     * @throws NeedLoginException 用户未登录
     * @throws IOException        读写数据异常
     * @throws ContentException   评论涉及敏感词汇
     */
    @RequestMapping(value = "/replyComment", method = RequestMethod.POST)
    ApiResult replyComment(Output<Long> data, @RequestParam(value = "id") Long id
            , @RequestParam(value = "content") String content) throws NeedLoginException, IOException, ContentException;



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
}
