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
import com.huotu.huobanplus.sns.entity.*;
import com.huotu.huobanplus.sns.model.AppArticleCommentModel;
import com.huotu.huobanplus.sns.model.AppCircleArticleCommentsModel;
import com.huotu.huobanplus.sns.model.AppCircleArticleModel;
import com.huotu.huobanplus.sns.model.AppUserConcermListModel;
import com.huotu.huobanplus.sns.model.common.AppCode;
import com.huotu.huobanplus.sns.model.common.ArticleType;
import com.huotu.huobanplus.sns.model.common.ReportTargetType;
import com.huotu.huobanplus.sns.utils.ContractHelper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Administrator on 2016/11/1.
 */
public class UserControllerImplTest extends CommonTestBase {

    @Autowired
    private Environment env;
    @Autowired
    private RedisTemplate<String, Integer> longRedisTemplate;

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
            randomConcernOther(concernUser);
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
        Thread.sleep(1000L);
//        System.out.println(articleId);
        assertEquals("粉丝文章数量", userArticleRepository.countByArticleId(articleId).orElse(0L).intValue(), index + 1);
    }

    @Test
    public void commentArticle() throws Exception {
        Article article = randomArticle();
        String data = mockMvc.perform(device.postApi("/user/commentArticle").param("id", article.getId() + "")
                .param("content", UUID.randomUUID().toString()).build())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()))
                .andReturn().getResponse().getContentAsString();
        Long commentId = Long.parseLong(JsonPath.read(data, "$.resultData.data").toString());
        List<AppCircleArticleCommentsModel> list = circleArticleCommentsModelRedisTemplate.opsForList()
                .range(ContractHelper.articleCommentFlag + article.getId(), 0L, -1);
        assertEquals("评论id", list.get(0).getPid(), commentId);

    }

    @Test
    public void report() throws Exception {
        Article article = randomArticle();
        mockMvc.perform(device.postApi("/user/report").param("id", article.getId() + "")
                .param("note", UUID.randomUUID().toString())
                .param("type", "1").build())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()));
        List<Report> list = reportRepository.findByTargetIdAndReportTargetType(article.getId(), ReportTargetType.Article);
        assertEquals("举报文章列表", list.get(0).getTargetId(), article.getId());
    }

    @Test
    public void myConcern() throws Exception {
        int index = random.nextInt(20);
        List<User> list = new ArrayList<>();
        for (int i = 0; i < index; i++) {
            User otherUser = randomUser();
            list.add(otherUser);
        }

        for (User concernUser : list) {
            randomConcernOwner(concernUser);
        }
        int length;
        if (index > 10) length = 10;
        else length = index;
        String body = mockMvc.perform(device.getApi("/user/myConcern").build())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()))
                .andReturn().getResponse().getContentAsString();
//                .andExpect(jsonPath("$.resultData.list.length()").value(length));
        List<AppUserConcermListModel> models = JsonPath.read(body, "$.resultData.list");
        assertEquals("关注列表长度", models.size(), length);
    }

    @Test
    public void myConcerned() throws Exception {
        int index = random.nextInt(20);
        List<User> list = new ArrayList<>();
        for (int i = 0; i < index; i++) {
            User otherUser = randomUser();
            list.add(otherUser);
        }

        for (User concernUser : list) {
            randomConcernOther(concernUser);
        }
        int length;
        if (index > 10) length = 10;
        else length = index;
        String body = mockMvc.perform(device.getApi("/user/myConcerned").build())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()))
                .andReturn().getResponse().getContentAsString();
//                .andExpect(jsonPath("$.resultData.list.length()").value(length));
        List<AppUserConcermListModel> models = JsonPath.read(body, "$.resultData.list");
        assertEquals("粉丝列表长度", models.size(), length);
    }

    @Test
    public void concernIndex() throws Exception {
        int index = random.nextInt(20);
        User anotherUser = randomUser();
        randomConcernOwner(anotherUser);
        Circle circle = randomCircle();
        for (int i = 0; i < index; i++) {
            String str = UUID.randomUUID().toString();
            articleService.addArticleResult(ArticleType.Normal.getValue(),
                    str, anotherUser, str, str, circle.getId());
        }
        int size = index > 10 ? 10 : index;
        String body = mockMvc.perform(device.getApi("/user/concernIndex").build())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()))
                .andReturn().getResponse().getContentAsString();
        List<AppCircleArticleModel> models = JsonPath.read(body, "$.resultData.articleList");
        assertEquals("文章列表长度", models.size(), size);

    }

    @Test
    public void articleClick() throws Exception {
        Article article = randomArticle();
        mockMvc.perform(device.postApi("/user/articleClick").param("id", article.getId() + "").build())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()));
        Article newArticle = articleRepository.getOne(article.getId());
        assertEquals("点赞数", newArticle.getClick(), 1L);

    }

//    @Test
//    public void testAddRedisList() throws Exception {
//        ListOperations<String, AppArticleCommentModel> listOperations = articleCommentRedisTemplate.opsForList();
//        System.out.println(articleCommentRedisTemplate.getExpire("_test_list_" + 1));
//        listOperations.leftPush("_test_list_" + 1, null);
//        System.out.println(articleCommentRedisTemplate.hasKey("_test_list_" + 1));
//        articleCommentRedisTemplate.expire("_test_list_" + 1, 1, TimeUnit.SECONDS);
//
//        Thread.sleep(2000);
//        System.out.println(articleCommentRedisTemplate.hasKey("_test_list_" + 1));
//        System.out.println(articleCommentRedisTemplate.getExpire("_test_list_" + 1));
//
//    }

    @Test
//    @Transactional
    public void replyComment() throws Exception {
        Article article = randomArticle();
        int index = random.nextInt(20);
//        articleCommentRedisTemplate.multi();
        ArticleComment comment = randomArticleComment(article, null, user);
//        articleCommentRedisTemplate.exec();

//        String data = mockMvc.perform(device.postApi("/user/commentArticle").param("id", article.getId() + "")
//                .param("content", UUID.randomUUID().toString()).build())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()))
//                .andReturn().getResponse().getContentAsString();
//        Long commentId = Long.parseLong(JsonPath.read(data, "$.resultData.data").toString());
//        Thread.sleep(1000L);
//        ArticleComment comment = articleCommentRepository.getOne(commentId);
        List<String> idList = new ArrayList<>();
        String replyId = comment.getId() + "";
        idList.add(replyId);
        for (int i = 0; i < index; i++) {
            String body = mockMvc.perform(device.postApi("/user/replyComment")
                    .param("id", replyId)
                    .param("content", UUID.randomUUID().toString()).build())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()))
                    .andReturn().getResponse().getContentAsString();
            replyId = JsonPath.read(body, "$.resultData.data") + "";
            idList.add(replyId);
        }
//        circleArticleCommentsModelRedisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
//        articleCommentRedisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        ListOperations<String, AppCircleArticleCommentsModel> circleArticleCommentsModelListOperations =
                circleArticleCommentsModelRedisTemplate.opsForList();
        ListOperations<String, AppArticleCommentModel> listOperations = articleCommentRedisTemplate.opsForList();
        List<AppCircleArticleCommentsModel> list = circleArticleCommentsModelListOperations
                .range(ContractHelper.articleCommentFlag + article.getId(), 0L, -1);
        assertEquals("评论列表长度", list.size(), index + 1);
        List<AppArticleCommentModel> replayList = listOperations.range(ContractHelper.articleReplyCommentFlag + replyId, 0L, -1);
        assertEquals("冗余评论列表长度", replayList.size(), index);
        String str = "";
        for (String id : idList) {
            ArticleComment newComment = articleCommentRepository.getOne(Long.parseLong(id));
            assertEquals("路径存储", str, newComment.getPath() == null ? "" : newComment.getPath());
            str = str.length() == 0 ? "," + newComment.getId() + "," : str + newComment.getId() + ",";
        }
        String removeId = idList.get(random.nextInt(idList.size() - 1));
        ArticleComment removeComment = articleCommentRepository.getOne(Long.parseLong(removeId));
        List<AppArticleCommentModel> models = redisService.getArticleCommentModelList(removeComment.getId());
        List<ArticleComment> removeList;
        if (null == removeComment.getPath()) {
            removeList = articleCommentRepository.findByPathLike("%," + removeComment.getId() + ",%");
        } else {
            removeList = articleCommentRepository.findByPathLike("%" + removeComment.getPath() + "%");
        }
        System.out.println(removeList);
    }

    @Test
    public void testRedisTest() throws Exception {
        longRedisTemplate.setValueSerializer(new GenericToStringSerializer<>(Integer.class));
        ValueOperations<String, Integer> valueOperations = longRedisTemplate.opsForValue();
        System.out.println(valueOperations.setIfAbsent("testNum", 0));
        valueOperations.increment("testNum", -1);
        System.out.println(valueOperations.get("testNum"));
    }

}