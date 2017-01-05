package com.huotu.huobanplus.sns.controller.impl.app;

import com.huotu.common.api.ApiResult;
import com.huotu.common.api.Output;
//import com.huotu.huobanplus.common.entity.Merchant;
//import com.huotu.huobanplus.common.repository.MerchantRepository;
import com.huotu.huobanplus.sns.controller.app.SecurityController;
import com.huotu.huobanplus.sns.exception.*;
import com.huotu.huobanplus.sns.mallrepository.MallMerchantRepository;
import com.huotu.huobanplus.sns.model.common.AppCode;
import com.huotu.huobanplus.sns.model.common.CodeType;
import com.huotu.huobanplus.sns.model.common.VerificationType;
import com.huotu.huobanplus.sns.service.CommonConfigService;
import com.huotu.huobanplus.sns.service.UserService;
import com.huotu.huobanplus.sns.service.VerificationService;
import com.huotu.huobanplus.sns.utils.EnumHelper;
import com.huotu.huobanplus.sns.utils.StringHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2016/10/28.
 */
@Controller
public class SecurityControllerImpl implements SecurityController {

    private static Log log = LogFactory.getLog(SecurityControllerImpl.class);


    @Autowired
    private VerificationService verificationService;

    @Autowired
    private UserService userService;

    @Autowired
    private MallMerchantRepository mallMerchantRepository;

    @Autowired
    private CommonConfigService commonConfigService;


    @Override
    public ApiResult getSecondDomain(Output<String> data, @RequestParam("customerId") Long customerId) {
        String subDomain = mallMerchantRepository.findSubDomainByMerchantId(customerId);
        data.outputData(subDomain + "." + commonConfigService.getMallDomain());
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult weixinLogin(Output<String> data
            , @RequestParam("customerId") Long customerId
            , @RequestParam(value = "openId") String openId
            , @RequestParam(value = "nickName", required = false) String nickName
            , @RequestParam(value = "imageUrl", required = false) String imageUrl) throws WeixinLoginFailException {

        userService.weixinLogin(customerId, openId, nickName, imageUrl);
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult sendCode(Output<Boolean> voiceAble
            , @RequestParam("customerId") Long customerId
            , @RequestParam("phone") String phone
            , @RequestParam("type") Integer type
            , @RequestParam("codeType") Integer codeType)
            throws VericationCodeIntervalException, WrongMobileException, NotSupportVoiceException, MessageInternetException, MobileExistException {
        //todo 需要根据costomerid进行修改
        voiceAble.outputData(verificationService.supportVoice());
        VerificationType verificationType = EnumHelper.getEnumType(VerificationType.class, type);
        Date date = new Date();

        Random rnd = new Random();
        String code = StringHelper.RandomNum(rnd, 4);
        verificationService.sendCode(customerId, phone, VerificationService.VerificationProject.fanmore, code, date, verificationType, codeType != null ? EnumHelper.getEnumType(CodeType.class, codeType) : CodeType.text);


        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult mobileLogin(Output<String> data
            , @RequestParam("customerId") Long customerId
            , @RequestParam("phone") String phone
            , @RequestParam("password") String password
            , @RequestParam(value = "openId", required = false) String openId
            , @RequestParam(value = "nickName", required = false) String nickName
            , @RequestParam(value = "imageUrl", required = false) String imageUrl) throws UnsupportedEncodingException, UserNamePasswordInvoidException, PasswordLengthLackException, MobileInvoidException, MobileNotExistException {
        data.outputData(userService.userLogin(customerId, phone, password
                , openId
                , nickName
                , imageUrl));
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult mobileRegister(Output<String> data
            , @RequestParam("customerId") Long customerId
            , @RequestParam("phone") String phone
            , @RequestParam("code") String code
            , @RequestParam("password") String password
            , @RequestParam(value = "openId", required = false) String openId
            , @RequestParam(value = "nickName", required = false) String nickName
            , @RequestParam(value = "imageUrl", required = false) String imageUrl) throws PasswordLengthLackException, UnsupportedEncodingException
            , VerificationCodeInvoidException, MobileInvoidException, MobileExistException, VerificationCodeDuedException {
        data.outputData(userService.userRegister(customerId, phone, code, password
                , openId
                , nickName
                , imageUrl));
        return ApiResult.resultWith(AppCode.SUCCESS);
    }
}
