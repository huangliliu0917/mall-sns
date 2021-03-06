/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanplus.sns.service.impl;


import com.huotu.huobanplus.sns.entity.VerificationCode;
import com.huotu.huobanplus.sns.exception.MessageInternetException;
import com.huotu.huobanplus.sns.model.common.AppCode;
import com.huotu.huobanplus.sns.model.common.ResultModel;
import com.huotu.huobanplus.sns.service.VerificationService;
import com.huotu.huobanplus.sns.utils.SMSHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Formatter;
import java.util.Locale;

/**
 * @author CJ
 */
@Service
public class YimeiVerificationService extends AbstractVerificationService implements VerificationService {

    private static final Log log = LogFactory.getLog(YimeiVerificationService.class);
    @Autowired
    private Environment env;

    public YimeiVerificationService() {
        log.info("伊美短信平台使用中……");
    }

    @Override
    public boolean supportVoice() {
        return false;
    }

    @Override
    protected void doSend(VerificationProject project, VerificationCode code) throws MessageInternetException {
//        if (env.acceptsProfiles("test") && !env.acceptsProfiles("prod"))
//            return;

        String smsContent = new Formatter(Locale.CHINA).format(project.getFormat(), code.getCode()).toString();
        SMSHelper sms = new SMSHelper();
        ResultModel resultSMS = sms.send(code.getMobile(), smsContent);
        if (resultSMS.getCode() != 0)
            throw new MessageInternetException(AppCode.MESSAGE_INTERNET.getValue(), AppCode.MESSAGE_INTERNET.getName());
    }
}
