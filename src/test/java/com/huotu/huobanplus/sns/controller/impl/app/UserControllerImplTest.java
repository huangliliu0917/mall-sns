/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.controller.impl.app;

import com.huotu.huobanplus.sns.CommonTestBase;
import com.huotu.huobanplus.sns.entity.Article;
import com.huotu.huobanplus.sns.entity.Circle;
import com.huotu.huobanplus.sns.model.common.AppCode;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Administrator on 2016/11/1.
 */
public class UserControllerImplTest extends CommonTestBase {
    @Test
    public void concern() throws Exception {
        Circle circle = randomCircle();
        mockMvc.perform(device.postApi("/user/concern").param("id", circle.getId() + "").build())
                .andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()));
        assertEquals("用户关注数量", userCircleRepository.findByUserAndCircle(user, circle).size(), 1);
    }

    @Test
    public void cancelConcern() throws Exception {

    }

    @Test
    public void concernUser() throws Exception {

    }

    @Test
    public void cancelUser() throws Exception {

    }

    @Test
    public void publishArticle() throws Exception {

    }

    @Test
    public void commentArticle() throws Exception {

    }

    @Test
    public void report() throws Exception {

    }

    @Test
    public void myConcern() throws Exception {

    }

    @Test
    public void myConcerned() throws Exception {

    }

    @Test
    public void concernIndex() throws Exception {

    }

    @Test
    public void articleClick() throws Exception {

    }

    @Test
    public void replyComment() throws Exception {
        Article article = randomArticle();

        mockMvc.perform(device.postApi("/user/commentArticle").param("id", article.getId() + "")
                .param("content", UUID.randomUUID().toString()).build()).andDo(print());
    }
}