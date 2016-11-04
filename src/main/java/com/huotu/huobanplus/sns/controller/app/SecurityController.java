package com.huotu.huobanplus.sns.controller.app;

import com.huotu.common.api.ApiResult;
import com.huotu.common.api.Output;
import com.huotu.huobanplus.sns.exception.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

/**
 * app安全相关模块
 * Created by Administrator on 2016/10/28.
 */
@RequestMapping("/app/security")
public interface SecurityController {


    /**
     * 获取商家二级域名
     *
     * @param customerId 商户Id
     * @return 商家二级域名
     */
    @RequestMapping(value = "/getSecondDomain", method = RequestMethod.POST)
    ApiResult getSecondDomain(Output<String> data, @RequestParam("customerId") Long customerId);


    /**
     * 微信登录
     *
     * @param data     token数据
     * @param openId   openid
     * @param nickName 用户昵称
     * @param imageUrl 用户头像
     * @return <ul>
     * <li>1 成功返回token数据</li>
     * <li>20002 微信登录失败，没有注册手机</li>
     * </ul>
     */
    @RequestMapping(value = "/weixinLogin", method = RequestMethod.POST)
    ApiResult weixinLogin(Output<String> data
            , @RequestParam("customerId") Long customerId
            , @RequestParam(value = "openId") String openId
            , @RequestParam(value = "nickName", required = false) String nickName
            , @RequestParam(value = "imageUrl", required = false) String imageUrl) throws WeixinLoginFailException;

    /**
     * 获取验证码 发送验证码
     *
     * @param voiceAble  是否支持语音播报
     * @param customerId 商户id
     * @param phone      String(11)
     * @param type       类型1：注册    2：忘记密码    3:绑定(更换)手机
     * @param codeType   0文本 1语音
     * @return <ul>
     * <li>20003 不合法的手机号</li>
     * <li>20104 验证码发送间隔为90秒</li>
     * <li>20109 短信发送通道不稳定，请重新尝试</li>
     * <li>20108  "还不支持的语音播报"</li>
     * </ul>
     */
    @RequestMapping(value = "/sendCode", method = RequestMethod.POST)
    ApiResult sendCode(Output<Boolean> voiceAble
            , @RequestParam("customerId") Long customerId
            , @RequestParam("phone") String phone
            , @RequestParam("type") Integer type
            , @RequestParam("codeType") Integer codeType)
            throws VericationCodeIntervalException, WrongMobileException, NotSupportVoiceException, MessageInternetException;

    /***
     * 用户手机登录
     * @param data token数据
     * @param customerId 商户id
     * @param phone 手机号
     * @param password 密码
     * @param openId     openid
     * @param nickName   用户昵称
     * @param imageUrl   用户头像
     * @return* token数据
     * <ul>
     *     <li>20004, "错误的用户名密码"</li>
     *     <li>20005, "密码长度需要大于6"</li>
     *     <li>20003, "不合法的手机号"</li>
     *     <li>20007,"手机号不存在，请换用其他手机号"</li>
     * </ul>
     */
    @Deprecated
    @RequestMapping(value = "/mobileLogin", method = RequestMethod.POST)
    ApiResult mobileLogin(Output<String> data
            , @RequestParam("customerId") Long customerId
            , @RequestParam("phone") String phone
            , @RequestParam("password") String password
            , @RequestParam(value = "openId", required = false) String openId
            , @RequestParam(value = "nickName", required = false) String nickName
            , @RequestParam(value = "imageUrl", required = false) String imageUrl)
            throws UnsupportedEncodingException, UserNamePasswordInvoidException, PasswordLengthLackException, MobileInvoidException, MobileNotExistException;


    /**
     * 用户手机注册
     *
     * @param data       token数据
     * @param customerId 商户id
     * @param phone      手机号
     * @param code       验证码
     * @param password   密码
     * @param openId     openid
     * @param nickName   用户昵称
     * @param imageUrl   用户头像
     * @return* token数据
     * <ul>
     * <li>20107, "验证码到期了"</li>
     * <li>20106, "验证码错误"</li>
     * <li>20005, "密码长度需要大于6"</li>
     * <li>20003, "不合法的手机号"</li>
     * <li>20006,"手机号已存在，请换用其他手机号"</li>
     * </ul>
     */
    ApiResult mobileRegister(Output<String> data
            , @RequestParam("customerId") Long customerId
            , @RequestParam("phone") String phone
            , @RequestParam("code") String code
            , @RequestParam("password") String password
            , @RequestParam(value = "openId", required = false) String openId
            , @RequestParam(value = "nickName", required = false) String nickName
            , @RequestParam(value = "imageUrl", required = false) String imageUrl)
            throws VerificationCodeDuedException, VerificationCodeInvoidException
            , PasswordLengthLackException, UnsupportedEncodingException, MobileInvoidException, MobileExistException;

}
