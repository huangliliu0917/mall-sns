package com.huotu.huobanplus.sns.controller.impl.app;

import com.huotu.huobanplus.sns.base.BaseTest;
import com.huotu.huobanplus.sns.base.Device;
import com.huotu.huobanplus.sns.base.DeviceType;
import com.huotu.huobanplus.sns.boot.*;
import com.huotu.huobanplus.sns.entity.VerificationCode;
import com.huotu.huobanplus.sns.mallservice.MallUserService;
import com.huotu.huobanplus.sns.model.common.AppCode;
import com.huotu.huobanplus.sns.model.common.CodeType;
import com.huotu.huobanplus.sns.model.common.VerificationType;
import com.huotu.huobanplus.sns.repository.UserRepository;
import com.huotu.huobanplus.sns.repository.VerificationCodeRepository;
import com.huotu.huobanplus.sns.utils.StringHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by Administrator on 2016/11/1.
 */

@WebAppConfiguration
@ActiveProfiles({"development"})
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BootConfig.class
        , MvcConfig.class
        , MallBootConfig.class
        , ContainerRuntimeConfig.class
        , LocalRuntimeConfig.class
        , RedisConfig.class})
@Transactional
public class SecurityControllerImplTest extends BaseTest {
    private static Log log = LogFactory.getLog(WebControllerImplTest.class);

    /**
     * 携带token的设备
     */
    private Device device;

    @Autowired
    private MallUserService mallUserService;

    private Long mockUserId;

    private String mockMobile;


    @Before
    public void prepareDevice() throws UnsupportedEncodingException {
        device = Device.newDevice(DeviceType.Android);
        mockMobile = generateMobile();
        mockUserId = mallUserService.userRegister(customerId, mockMobile, "", "", "", "");
    }

    @Test
    public void getSecondDomain() throws Exception {
//        mockMvc.perform(device.getApi("/app/security/getSecondDomain").build())
//                .andDo(print());
    }

    @Test
    public void weixinLogin() throws Exception {
//        mockMvc.perform(device.getApi("/app/security/weixinLogin").build())
//                .andDo(print());
    }

    @Test
    public void sendCode() throws Exception {
        mockMvc.perform(device.getApi("/app/security/sendCode")
                .param("customerId",customerId.toString())
                .param("phone","18368893860")
                .param("type","1")
                .param("codeType","0")
                .build())
                .andDo(print());
    }

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Test
    public void mobileLogin() throws Exception {
        String mobile = generateMobile();

        Random random = new Random();
        String code = StringHelper.RandomNum(random, 4);
        sendCode(mobile, code);
        mockMvc.perform(device.postApi("/security/mobileLogin")
                .param("customerId", customerId.toString())
                .param("phone", mobile)
                .param("code", code)
                .build())
                .andExpect(Device.SnsStatus(AppCode.SUCCESS))
                .andDo(print());


        random = new Random();
        code = StringHelper.RandomNum(random, 4);
        sendCode(mockMobile, code);
        mockMvc.perform(device.postApi("/security/mobileLogin")
                .param("customerId", customerId.toString())
                .param("phone", mockMobile)
                .param("code", code)
                .build())
                .andExpect(Device.SnsStatus(AppCode.SUCCESS))
                .andDo(print());
    }

    private void sendCode(String mobile, String code) throws Exception {
        VerificationCode verificationCode = verificationCodeRepository.findByCustomerIdAndMobileAndTypeAndCodeType(customerId, mobile, VerificationType.BIND_REGISTER, CodeType.text);
        if (verificationCode == null) {
            verificationCode = new VerificationCode();
            verificationCode.setMobile(mobile);
            verificationCode.setType(VerificationType.BIND_REGISTER);
            verificationCode.setCodeType(CodeType.text);
        }
        verificationCode.setSendTime(new Date());
        verificationCode.setCode(code);
        verificationCode.setCustomerId(customerId);
        verificationCodeRepository.save(verificationCode);
    }

}