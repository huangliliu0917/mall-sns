package com.huotu.huobanplus.sns.base;


import com.huotu.huobanplus.sns.entity.User;
import com.huotu.huobanplus.sns.repository.UserRepository;
import com.huotu.huobanplus.sns.utils.StringHelper;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lgh on 2016/1/12.
 */

public class BaseTest {

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected Long customerId = 3447L;

    @Before
    public void createMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
//
//    /**
//     * 生成一个没有绑定手机的用户
//     *
//     * @param password
//     * @param userRepository
//     * @return
//     * @throws UnsupportedEncodingException
//     */
//    public User generateUserWithoutMobile(@NotNull String password, UserRepository userRepository) throws UnsupportedEncodingException {
//        String userName = generateInexistentMobile(userRepository);
//        User user = new User();
//        user.setEnabled(true);
//        user.setRegTime(new Date(System.currentTimeMillis() + new Random().nextInt(360 * 30 * 24 * 60 * 60)));
//        user.setUsername(userName);
//        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes("UTF-8")).toLowerCase());
//        user.setUserHead(staticResourceService.USER_HEAD_PATH + "defaultH.jpg");
//        return userRepository.saveAndFlush(user);
//    }
//
//    /**
//     * 生成一个班定了手机 但是没有token的用户
//     *
//     * @param password
//     * @param userRepository
//     * @return
//     * @throws UnsupportedEncodingException
//     */
//    public User generateUserWithMobileWithoutToken(String password, UserRepository userRepository) throws UnsupportedEncodingException {
//        User user = generateUserWithoutMobile(password, userRepository);
//        user.setMobileBinded(true);
//        user.setRegTime(new Date(System.currentTimeMillis() + new Random().nextInt(360 * 30 * 24 * 60 * 60)));
//        user.setMobile(user.getUsername());
//        user.setUserHead(staticResourceService.USER_HEAD_PATH + "defaultH.jpg");
//        user.setBuyAndTurnType(CommonEnum.BuyAndTurnType.buy);
//        return userRepository.saveAndFlush(user);
//    }
//
//    /**
//     * 生成一个绑定了手机而且拥有登录token的用户
//     *
//     * @param password
//     * @param userRepository
//     * @return
//     * @throws UnsupportedEncodingException
//     */
//    public User generateUserWithMobileWithToken(String password, UserRepository userRepository) throws UnsupportedEncodingException {
//        User user = generateUserWithMobileWithoutToken(password, userRepository);
//        user.setToken(DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes("UTF-8")));
//        user.setUserHead(staticResourceService.USER_HEAD_PATH + "defaultH.jpg");
//        user.setUserFromType(CommonEnum.UserFromType.register);
//        user.setBuyAndTurnType(CommonEnum.BuyAndTurnType.buy);
//        user.setMoney(new BigDecimal("1000000"));
//        return userRepository.saveAndFlush(user);
//    }
//

    /**
     * 返回一个不存在的手机号码
     *
     * @return 手机号码
     */
    public String generateMobile() {
        Random random = new Random();
        return StringHelper.RandomNum(random, 11);
    }

    @Autowired
    private UserRepository userRepository;

    public User createUser(Long customerId
            , Long userId
            , String mobile
            , String openId
            , String nickName
            , String imageUrl) {
        User user = new User();
        user.setId(userId);
        user.setCustomerId(customerId);
        user.setMobile(mobile);
        user.setOpenId(openId);
        user.setNickName(nickName);
        user.setImgURL(imageUrl);
        user.setCreateDate(new Date());
//            user.setLevel();//todo 获取最低级别
        user.setRank(100000000L);
//            user.setTags();
        user = userRepository.save(user);
        return user;
    }
}
