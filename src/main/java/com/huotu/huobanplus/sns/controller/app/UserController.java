package com.huotu.huobanplus.sns.controller.app;

import com.huotu.common.api.ApiResult;
import com.huotu.common.api.Output;
import com.huotu.huobanplus.sns.model.AppCircleArticleModel;
import com.huotu.huobanplus.sns.model.AppUserConcermListModel;
import com.huotu.huobanplus.sns.model.common.ReportTargetType;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @RequestMapping("/concerm")
    ApiResult concerm(Long id) throws Exception;

    /**
     * 取消关注圈子
     *
     * @param id 圈子id
     * @return
     * @throws Exception
     */
    @RequestMapping("/cancelConcerm")
    ApiResult cancelConcerm(Long id) throws Exception;

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
    @RequestMapping("/publishArticle")
    ApiResult publishArticle(Long id, String name, String content, String pictureUrl, Long circleId) throws Exception;


    /**
     * 评论文章
     * 注意：请对内容进行敏感词校验
     *
     * @param id      文章Id
     * @param content 评论内容
     * @return
     */
    @RequestMapping("/commentArticle")
    ApiResult commentArticle(Long id, String content) throws Exception;


    /**
     * 举报
     *
     * @param type 类型
     * @param id   类型对应 id
     * @param note 说明
     * @return
     * @throws Exception
     */
    @RequestMapping("/report")
    ApiResult report(ReportTargetType type, Long id, String note) throws Exception;

    /**
     * 我的关注
     * todo 暂定从redis获取数据
     *
     * @param lastId 上一个id
     * @return
     * @throws Exception
     */
    @RequestMapping("/myConcerm")
    ApiResult myConcerm(Output<AppUserConcermListModel[]> list, Long lastId) throws Exception;

    /**
     * 我的粉丝
     *
     * @param list
     * @param lastId
     * @return
     * @throws Exception
     */
    @RequestMapping("/myConcermed")
    ApiResult myConcermed(Output<AppUserConcermListModel[]> list, Long lastId) throws Exception;


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
