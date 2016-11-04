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
import com.huotu.huobanplus.sns.entity.User;
import com.huotu.huobanplus.sns.model.common.AppCode;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()));
        assertEquals("用户关注圈子数量", userCircleRepository.findByUserAndCircle(user, circle).size(), 1);
    }

    @Test
    public void cancelConcern() throws Exception {
        Circle circle = randomCircle();
        mockMvc.perform(device.postApi("/user/concern").param("id", circle.getId() + "").build())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()));
        Circle otherCircle = circleRepository.getOne(circle.getId());
        assertEquals("圈子关注量", otherCircle.getUserAmount(), 1);
        mockMvc.perform(device.postApi("/user/cancelConcern").param("id", circle.getId() + "").build())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()));
        assertEquals("用户关注圈子数量", userCircleRepository.findByUserAndCircle(user, circle).size(), 0);
    }

    @Test
    public void concernUser() throws Exception {
        User newUser = randomUser();
        mockMvc.perform(device.postApi("/user/concernUser").param("id", newUser.getId() + "").build())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()));
        assertEquals("用户关注数量", concernRepository.findByUser(user).size(), 1);
    }

    @Test
    public void cancelUser() throws Exception {
        User newUser = randomUser();
        mockMvc.perform(device.postApi("/user/concernUser").param("id", newUser.getId() + "").build())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()));
        User otherUser = userRepository.getOne(newUser.getId());
        assertEquals("用户粉丝量", otherUser.getFansAmount(), 1);
        User ownUser = userRepository.getOne(user.getId());
        assertEquals("用户关注量", ownUser.getUserAmount(), 1);
        assertEquals("关注列表", concernRepository.findByUserAndToUser(user, newUser).size(), 1);
        mockMvc.perform(device.postApi("/user/cancelUser").param("id", newUser.getId() + "").build())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()));
        assertEquals("用户关注数量", concernRepository.findByUserAndToUser(user, newUser).size(), 0);
    }

    @Test
    public void publishArticle() throws Exception {
        int index = random.nextInt(20);
        List<User> list = new ArrayList<>();
        for (int i = 0; i < index; i++) {
            User otherUser = randomUser();
            list.add(otherUser);
        }

        for (User concernUser : list) {
            randomConcern(concernUser);
        }
        Circle circle = randomCircle();
        String data = mockMvc.perform(device.postApi("/user/publishArticle").param("name", UUID.randomUUID().toString())
                .param("content", UUID.randomUUID().toString())
                .param("circleId", circle.getId().toString()).build())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue())).andReturn().getResponse().getContentAsString();
        Circle newCircle = circleRepository.getOne(circle.getId());
        assertEquals("圈子文章数量", newCircle.getArticleAmount(), 1);
        User newUser = userRepository.getOne(user.getId());
        assertEquals("用户文章数量", newUser.getArticleAmount(), 1);
        Long articleId = Long.parseLong(JsonPath.read(data, "$.resultData.data").toString());

//        System.out.println(articleId);
        assertEquals("粉丝文章数量", userArticleRepository.countByArticleId(articleId).orElse(0L).intValue(), index + 1);
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

    @Test
    public void test() throws Exception {
        mockMvc.perform(device.postApi("/user/test").param("id", "1").build())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()));
    }
}