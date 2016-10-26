package com.huotu.huobanplus.sns.controller.app;

import com.huotu.common.api.ApiResult;
import com.huotu.common.api.Output;
import com.huotu.huobanplus.sns.model.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

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
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    ApiResult user(Output<AppUserModel> data
            , @RequestParam("id") Long id) throws Exception;

    /**
     * 浏览用户文章
     *
     * @param articleList 文章列表
     * @param id          用户id
     * @param lastId      上一篇文章id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userArticleList", method = RequestMethod.GET)
    ApiResult userArticleList(Output<AppCircleArticleModel[]> articleList
            , @RequestParam(value = "id") Long id
            , @RequestParam(value = "lastId",required = false) Long lastId) throws Exception;


    /**
     * 搜索
     *
     * @param articleList 文章列表
     * @param key         关键字
     * @param lastId      上一个id（用户Id 圈子id 话题Id）
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    ApiResult search(Output<AppCircleIndexArticleListModel[]> articleList
            , @RequestParam("key") String key
            , @RequestParam(value = "lastId",required = false) Long lastId) throws Exception;


    /**
     * 获取验证码 发送验证码
     *
     * @param mobile 手机号
     * @return
     */
    @RequestMapping(value = "/sendCheckCode", method = RequestMethod.GET)
    ApiResult sendCheckCode(@RequestParam("mobile") String mobile);

    /***
     * 用户注册/登录
     * @param data token数据
     * @param mobile 手机号
     * @param code 验证码
     * @return
     */
    @RequestMapping(value = "/userLogin", method = RequestMethod.GET)
    ApiResult userLogin(Output<String> data
            , @RequestParam("mobile") String mobile
            , @RequestParam("code") String code);


//    /***
//     * 用户登录/注册
//     * @param data token数据
//     * @param userName 用户名 只限手机号
//     * @param password 密码
//     * @return
//     */
//    @RequestMapping("/userLogin")
//    @Deprecated
//    ApiResult userLogin(Output<String> data, String userName, String password);

//
//    /**
//     * 检测token
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping("/checkToken")
//    @Deprecated
//    ApiResult checkToken(HttpServletRequest request);
}
