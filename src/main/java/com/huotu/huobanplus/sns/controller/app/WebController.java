package com.huotu.huobanplus.sns.controller.app;

import com.huotu.common.api.ApiResult;
import com.huotu.common.api.Output;
import com.huotu.huobanplus.sns.model.*;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 网站
 * Created by Administrator on 2016/9/29.
 */
@RequestMapping("/app/web")
public interface WebController {

    /**
     * 浏览用户
     *
     * @param data 用户数据
     * @param id   用户id
     * @return
     * @throws Exception
     */
    @RequestMapping("/user")
    ApiResult user(Output<AppUserModel> data, Long id) throws Exception;

    /**
     * 浏览用户文章
     *
     * @param articleList 文章列表
     * @param id          用户id
     * @param lastId      上一篇文章id
     * @return
     * @throws Exception
     */
    @RequestMapping("/userArticleList")
    ApiResult userArticleList(Output<AppCircleArticleModel[]> articleList, Long id, Long lastId) throws Exception;


    /**
     * 搜索
     *
     * @param userList    用户列表
     * @param circleList  圈子列表
     * @param articleList 文章列表
     * @param type        0用户 1小组 2话题
     * @param key         关键字
     * @param lastId      上一个id（用户Id 圈子id 话题Id）
     * @return
     * @throws Exception
     */
    @RequestMapping("/search")
    ApiResult search(Output<AppUserConcermListModel[]> userList, Output<AppCircleIndexListModel[]> circleList, Output<AppCircleIndexArticleListModel[]> articleList
            , Integer type, String key, Long lastId) throws Exception;
}
