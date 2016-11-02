package com.huotu.huobanplus.sns.service.impl;


import com.huotu.huobanplus.sns.entity.VerificationCode;
import com.huotu.huobanplus.sns.exception.MessageInternetException;
import com.huotu.huobanplus.sns.exception.NotSupportVoiceException;
import com.huotu.huobanplus.sns.exception.VericationCodeIntervalException;
import com.huotu.huobanplus.sns.exception.WrongMobileException;
import com.huotu.huobanplus.sns.model.common.AppCode;
import com.huotu.huobanplus.sns.model.common.CodeType;
import com.huotu.huobanplus.sns.model.common.VerificationType;
import com.huotu.huobanplus.sns.repository.VerificationCodeRepository;
import com.huotu.huobanplus.sns.service.VerificationService;
import com.huotu.huobanplus.sns.utils.RegexHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author CJ
 */
public abstract class AbstractVerificationService implements VerificationService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;
    /**
     * 允许间隔60秒
     */
    private int gapSeconds = 60;

    @Transactional
    public void sendCode(Long customerId, String mobile, VerificationProject project, String code, Date currentDate, VerificationType type, CodeType sentType)
            throws VericationCodeIntervalException, NotSupportVoiceException, WrongMobileException, MessageInternetException {
        if (!RegexHelper.IsValidMobileNo(mobile)) {
            throw new WrongMobileException(AppCode.ERROR_WRONG_MOBILE.getValue(), AppCode.ERROR_WRONG_MOBILE.getName());
        }
        if (sentType == null) {
            sentType = CodeType.text;
        }

        if (!supportVoice() && sentType == CodeType.voice) {
            throw new NotSupportVoiceException(AppCode.Not_Support_Voice.getValue(), AppCode.Not_Support_Voice.getName());
        }

        VerificationCode verificationCode = verificationCodeRepository.findByCustomerIdAndMobileAndTypeAndCodeType(customerId, mobile, type, sentType);
        if (verificationCode != null) {
            //刚刚发送过
            if (currentDate.getTime() - verificationCode.getSendTime().getTime() < gapSeconds * 1000) {
                throw new VericationCodeIntervalException(AppCode.VERICATIONCOD_EINTERVAL.getValue(), AppCode.VERICATIONCOD_EINTERVAL.getName());
            }
        } else {
            verificationCode = new VerificationCode();
            verificationCode.setMobile(mobile);
            verificationCode.setType(type);
            verificationCode.setCodeType(sentType);
            verificationCode.setCustomerId(customerId);
        }
        verificationCode.setSendTime(currentDate);
        verificationCode.setCode(code);
        verificationCode = verificationCodeRepository.save(verificationCode);

        doSend(project, verificationCode);
    }

    protected abstract void doSend(VerificationProject project, VerificationCode code) throws MessageInternetException;

//    @Transactional
//    public boolean verifyCode(String mobile, VerificationProject project, String code, Date currentDate, VerificationType type) throws IllegalArgumentException {
//        if (!RegexHelper.IsValidMobileNo(mobile)) {
//            throw new IllegalArgumentException("号码不对");
//        }
//        List<VerificationCode> codeList = verificationCodeRepository.findByMobileAndTypeAndSendTimeGreaterThan(mobile, type, new Date(currentDate.getTime() - gapSeconds * 1000));
//
////        Date limitTime=new Date(currentDate.getTime() - gapSeconds * 1000);
////        StringBuilder hql = new StringBuilder();
////        hql.append("select vc from VerificationCode as vc where vc.mobile=:mobile " +
////                " and vc.type=:type " );//+
////                //" and vc.sendTime>:limitTime ");
////        List<VerificationCode> codeList = verificationCodeRepository.queryHql(hql.toString(), query -> {
////            query.setParameter("mobile", mobile);
////            query.setParameter("type", type);
////           // query.setParameter("limitTime", limitTime);
////        });
//
//        for (VerificationCode verificationCode : codeList) {
//            if (verificationCode.getCode().equals(code))
//                return true;
//        }
//        return false;
//    }

    @Transactional
    public boolean checkPhoneAndCode(String phone, String code) {
        if (verificationCodeRepository.findByMobileAndCode(phone, code) != null) {
            return true;
        } else {
            return false;
        }
    }

    public int getGapSeconds() {
        return gapSeconds;
    }

    public void setGapSeconds(int gapSeconds) {
        this.gapSeconds = gapSeconds;
    }
}
