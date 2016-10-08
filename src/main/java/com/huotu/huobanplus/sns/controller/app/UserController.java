package com.huotu.huobanplus.sns.controller.app;

import com.huotu.common.api.ApiResult;
import com.huotu.common.api.Output;
import com.huotu.huobanplus.sns.model.AppCircleArticleModel;
import com.huotu.huobanplus.sns.model.AppUserConcermListModel;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户模块
 * Created by Administrator on 2016/9/28.
 */
@RequestMapping("/app/user")
public interface UserController {

    /**
     * 加入圈子
     *
     * @param id 圈子Id
     * @return
     * @throws Exception
     */
    @RequestMapping("/joinCircle")
    ApiResult joinCircle(Long id) throws Exception;

    /**
     * 退出圈子
     *
     * @param id 圈子id
     * @return
     * @throws Exception
     */
    @RequestMapping("/leaveCircle")
    ApiResult leaveCircle(Long id) throws Exception;

    /**
     * 关注用户
     *
     * @param id 用户Id
     * @return
     * @throws Exception
     */
    @RequestMapping("/concermUser")
    ApiResult concermUser(Long id) throws Exception;

    /**
     * 取消关注用户
     *
     * @param id 用户Id
     * @return
     * @throws Exception
     */
    @RequestMapping("/leaveUser")
    ApiResult leaveUser(Long id) throws Exception;


    /**
     * 评论文章
     *
     * @param id      文章Id
     * @param content 评论内容
     * @return
     */
    @RequestMapping("commentArticle")
    ApiResult commentArticle(Long id, String content) throws Exception;

    /**
     * 我的关注
     *
     * @param lastId 上一个id
     * @return
     * @throws Exception
     */
    @RequestMapping("/concerm")
    ApiResult concerm(Output<AppUserConcermListModel[]> list, Long lastId) throws Exception;

    /**
     * 我的粉丝
     *
     * @param list
     * @param lastId
     * @return
     * @throws Exception
     */
    @RequestMapping("/concermed")
    ApiResult concermed(Output<AppUserConcermListModel[]> list, Long lastId) throws Exception;


    /**
     * 关注首页
     *
     * @param articleList 文章列表
     * @param lastId      上一个文章id
     * @return
     * @throws Exception
     */
    @RequestMapping("concermIndex")
    ApiResult concermIndex(Output<AppCircleArticleModel[]> articleList, Long lastId) throws Exception;
}
