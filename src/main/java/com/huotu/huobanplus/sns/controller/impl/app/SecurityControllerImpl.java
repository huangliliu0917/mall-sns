package com.huotu.huobanplus.sns.controller.impl.app;

import com.huotu.common.api.ApiResult;
import com.huotu.common.api.Output;
//import com.huotu.huobanplus.common.entity.Merchant;
//import com.huotu.huobanplus.common.repository.MerchantRepository;
import com.huotu.huobanplus.sns.controller.app.SecurityController;
import com.huotu.huobanplus.sns.exception.InterrelatedException;
import com.huotu.huobanplus.sns.exception.VerificationCodeDuedException;
import com.huotu.huobanplus.sns.exception.VerificationCodeInvoidException;
import com.huotu.huobanplus.sns.exception.WeixinLoginFailException;
import com.huotu.huobanplus.sns.mallrepository.MallMerchantRepository;
import com.huotu.huobanplus.sns.model.common.AppCode;
import com.huotu.huobanplus.sns.model.common.CodeType;
import com.huotu.huobanplus.sns.model.common.VerificationType;
import com.huotu.huobanplus.sns.service.CommonConfigService;
import com.huotu.huobanplus.sns.service.UserService;
import com.huotu.huobanplus.sns.service.VerificationService;
import com.huotu.huobanplus.sns.utils.EnumHelper;
import com.huotu.huobanplus.sns.utils.RegexHelper;
import com.huotu.huobanplus.sns.utils.StringHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
    public ApiResult getSecondDomain(Output<String> data, Long customerId) {
        String subDomain = mallMerchantRepository.findSubDomainByMerchantId(customerId);
        data.outputData(subDomain + "." + commonConfigService.getMallDomain());
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult weixinLogin(Output<String> data, Long customerId, String openId
            , String nickName, String imageUrl) throws WeixinLoginFailException {

        userService.weixinLogin(customerId, openId, nickName, imageUrl);
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult sendCode(Output<Boolean> voiceAble, Long customerId, String phone, Integer type, Integer codeType) {
        //todo 需要根据costomerid进行修改
        voiceAble.outputData(verificationService.supportVoice());
        VerificationType verificationType = EnumHelper.getEnumType(VerificationType.class, type);
        Date date = new Date();
        // **********************************************************
        // 发送短信前处理
        if (!RegexHelper.IsValidMobileNo(phone)) {
            return ApiResult.resultWith(AppCode.ERROR_WRONG_MOBILE);
        }

        try {
            Random rnd = new Random();
            String code = StringHelper.RandomNum(rnd, 4);
            verificationService.sendCode(phone, VerificationService.VerificationProject.fanmore, code, date, verificationType, codeType != null ? EnumHelper.getEnumType(CodeType.class, codeType) : CodeType.text);

        } catch (IllegalStateException ex) {
            return ApiResult.resultWith(AppCode.ERROR_WRONG_VERIFICATION_INTERVAL);
        } catch (IllegalArgumentException ex) {
            return ApiResult.resultWith(AppCode.ERROR_WRONG_MOBILE);
        } catch (NoSuchMethodException ex) {
            //发送类别不受支持！
            return ApiResult.resultWith(AppCode.ERROR_SEND_MESSAGE_FAIL);
        } catch (InterrelatedException ex) {
            //第三方错误！
            log.error("短信发送失败", ex);
            return ApiResult.resultWith(AppCode.ERROR_SEND_MESSAGE_FAIL);
        }
        return ApiResult.resultWith(AppCode.SUCCESS);
    }

    @Override
    public ApiResult mobileLogin(Output<String> data, Long customerId, String phone, String code
            , String openId
            , String nickName
            , String imageUrl) throws UnsupportedEncodingException, VerificationCodeDuedException, VerificationCodeInvoidException {
        data.outputData(userService.userLogin(customerId, phone, code
                , openId
                , nickName
                , imageUrl));
        return ApiResult.resultWith(AppCode.SUCCESS);
    }
}
