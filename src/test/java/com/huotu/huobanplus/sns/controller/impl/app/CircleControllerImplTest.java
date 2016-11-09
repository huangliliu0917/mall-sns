package com.huotu.huobanplus.sns.controller.impl.app;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huotu.huobanplus.sns.CommonTestBase;
import com.huotu.huobanplus.sns.entity.*;
import com.huotu.huobanplus.sns.model.*;
import com.huotu.huobanplus.sns.model.common.AppCode;
import org.junit.Test;

import java.util.*;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Administrator on 2016/11/1.
 */
public class CircleControllerImplTest extends CommonTestBase {
    @Test
    public void circleIndexTop() throws Exception {
        List<Slide> slides=new ArrayList<>();

        for (int i=0;i<4;i++){
            Slide slide=new Slide();
            slide.setCustomerId(customerId);
            slide.setPictureUrl("/slideTest/"+i);
            slide.setUrl("www.baidu.com"+i);
            slide.setTitle(i+"");
            slides.add(slideRepository.saveAndFlush(slide));
        }
        Collections.sort(slides, new Comparator<Slide>() {
            @Override
            public int compare(Slide o1, Slide o2) {
                return o2.getId().intValue()-o1.getId().intValue();
            }
        });


        List<Circle> circles=new ArrayList<>();
        for(int i=0;i<10;i++){
            Circle circle=new Circle();
            circle.setEnabled(true);
            circle.setName("title"+i);
            circle.setCustomerId(customerId);
            circle.setPictureUrl("pictureUrl"+i);
            circle.setUserAmount(1000+i);
            circles.add(circleRepository.saveAndFlush(circle));
        }

        Collections.sort(circles, new Comparator<Circle>() {
            @Override
            public int compare(Circle o1, Circle o2) {
                return (int)o2.getUserAmount()-(int)o1.getUserAmount();
            }
        });



        AppCircleIndexSlideModel[] oldModels=slideService.getSlideModelList(slides);
        AppCircleIndexSuggestModel[] oldCircleModels=circleService.getCircleAppModels(circles);

        String result=mockMvc.perform(device.getApi("/circle/indexTop").build())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()))
                .andReturn().getResponse().getContentAsString();
//        assertEquals("用户关注数量", userCircleRepository.findByUserAndCircle(user, circle).size(), 1);
        JSONObject object=JSONObject.parseObject(result);
        JSONObject data=(JSONObject) object.get("resultData");
        JSONArray slideList=(JSONArray) data.get("slideList");
        JSONArray circleList=(JSONArray) data.get("suggestList");
        AppCircleIndexSlideModel[] newModels=JSONObject.toJavaObject(slideList, AppCircleIndexSlideModel[].class);
        AppCircleIndexSuggestModel[] newCircleModels=JSONObject.toJavaObject(circleList, AppCircleIndexSuggestModel[].class);

        assertEquals("banner",true, Arrays.equals(oldModels,newModels));
        assertEquals("circles",true,Arrays.equals(oldCircleModels,newCircleModels));
    }

    @Test
    public void circleIndexSuggestList() throws Exception {
        List<Circle> circles=new ArrayList<>();
        for(int i=0;i<12;i++){
            Circle circle=new Circle();
            circle.setEnabled(true);
            circle.setName("title"+i);
            circle.setCustomerId(customerId);
            if(i>5){
                circle.setSuggested(false);
            }else {
                circle.setSuggested(true);
            }
            circle.setPictureUrl("pictureUrl"+i);
            circle.setUserAmount(1000+i);
            circles.add(circleRepository.saveAndFlush(circle));
        }

        Collections.sort(circles, new Comparator<Circle>() {
            @Override
            public int compare(Circle o1, Circle o2) {
                return (int)o2.getUserAmount()-(int)o1.getUserAmount();
            }
        });
        AppCircleIndexSuggestModel[] oldCircleModels=circleService.getCircleAppModels(circles);

        String result=mockMvc.perform(device.getApi("/circle/circleIndexSuggestList")
//                .param("lastId",""+circles.get(0).getId())
                .build())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()))
                .andReturn().getResponse().getContentAsString();
        JSONObject object=JSONObject.parseObject(result);
        JSONObject data=(JSONObject) object.get("resultData");
        JSONArray circleList=(JSONArray) data.get("suggestList");
        AppCircleIndexSuggestModel[] newCircleModels=JSONObject.toJavaObject(circleList, AppCircleIndexSuggestModel[].class);

        assertEquals("circles",true,Arrays.equals(oldCircleModels,newCircleModels));

    }

    @Test
    public void circleIndexList() throws Exception {

        //当前用户ID
        User user=userRepository.findOne(mockUserId);


        User puser=new User();
        puser.setId(8888L);
        puser.setCustomerId(customerId);
        puser.setNickName("wy");
        puser=userRepository.saveAndFlush(puser);

        List<UserCircle> userCircles=new ArrayList<>();

        AppCircleIndexListModel[] models=new AppCircleIndexListModel[9];

        for(int i=0;i<9;i++){
            UserCircle userCircle=new UserCircle();

            Circle circle=new Circle();
            circle.setEnabled(true);
            circle=circleRepository.saveAndFlush(circle);

            List<Article> articles=new ArrayList<>();
            for(int j=0;j<4;j++){
                Article article=new Article();
                article.setEnabled(true);
                article.setPublisher(puser);
                article.setCustomerId(customerId);
                article.setCircle(circle);
                article.setName("圈子:"+i+" 文章:"+j);
                article.setComments(j*100L);
                article.setView(j*200L);
                articles.add(articleRepository.saveAndFlush(article));
            }

            Collections.sort(articles, new Comparator<Article>() {
                @Override
                public int compare(Article o1, Article o2) {
                    return o2.getId().intValue()-o1.getId().intValue();
                }
            });


            userCircle.setCircle(circle);
            userCircle.setUser(user);
            userCircle.setDate(new Date());
            userCircle.setCustomerId(customerId);
            userCircles.add(userCircleRepository.saveAndFlush(userCircle));


            List<AppCircleIndexArticleListModel> articleModels= userCircleService.getArticleModelList(articles);

            AppCircleIndexListModel model=new AppCircleIndexListModel();
            model.setList(articleModels);
            model.setPid(userCircle.getId());
            model.setCircleId(userCircle.getCircle().getId());
            models[i]=model;
        }

        String result=mockMvc.perform(device.getApi("/user/indexList")
                .build())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()))
                .andReturn().getResponse().getContentAsString();

        JSONObject object=JSONObject.parseObject(result);
        JSONObject data=(JSONObject) object.get("resultData");
        JSONArray circlelist=(JSONArray) data.get("circlelist");
        AppCircleIndexListModel[] newModels=JSONObject.toJavaObject(circlelist, AppCircleIndexListModel[].class);

        for(int i=0;i<newModels.length;i++){
            AppCircleIndexListModel newModel=newModels[i];
            AppCircleIndexListModel oldModel=models[8-i];
            assertEquals("article.length",3,newModel.getList().size());
            assertEquals("userCircle",newModel.getPid(),oldModel.getPid());
            assertEquals("article",newModel.getList().get(0).getArticleId(),oldModel.getList().get(0).getArticleId());
        }


    }

    @Test
    public void introduce() throws Exception {

    }

    @Test
    public void top() throws Exception {
        Circle circle=new Circle();
        circle.setEnabled(true);
        circle.setDate(new Date());
        circle.setName("sltCircle");
        circle=circleRepository.saveAndFlush(circle);
        AppCircleModel oldAppCircleModel=circleService.getAppCircleModel(circle);

        User currentUser=userRepository.findOne(mockUserId);

        List<Notice> notices=new ArrayList<>();
        for(int i=0;i<5;i++){
            Notice notice=new Notice();
            notice.setCustomerId(customerId);
            notice.setName("notice"+i);
            notice.setDate(new Date());
            notice.setEnabled(true);
            notice.setCircle(circle);
            notices.add(noticeRepository.saveAndFlush(notice));
        }

        List<AppCircleArticleModel> oldAppCircleArticleModels=new ArrayList<>();
        for(int i=0;i<10;i++){
            boolean flag=true;
            Article article=new Article();
            article.setCircle(circle);
            article.setDate(new Date());

            Level level=new Level();
            level.setCustomerId(customerId);
            level.setName("level"+i);
            level.setExperience(20L*i);
            level=levelRepository.saveAndFlush(level);


            User user=new User();
            user.setId(10000L+i);
            user.setLevel(level);
            user.setCustomerId(customerId);
            user.setNickName("testuser"+i);
            user=userRepository.saveAndFlush(user);
            article.setPublisher(user);
            if(i%4==0){
                flag=false;
            }
            article.setEnabled(i%4!=0);
            if(i%3==0){
                flag=false;
            }
            article.setTop(i%3!=0);
            article=articleRepository.saveAndFlush(article);

            AppCircleArticleModel model=articleService.getAppCircleArticleModel(article,null);
            if(i%2!=0){
                Concern concern=new Concern();
                concern.setCustomerId(customerId);
                concern.setDate(new Date());
                concern.setUser(currentUser);
                concern.setToUser(user);
                concern=concernRepository.saveAndFlush(concern);

                model.setConcerned(true);
            }
            if(flag){
                oldAppCircleArticleModels.add(model);
            }

        }
        Collections.reverse(oldAppCircleArticleModels);
        AppCircleNoticeModel[] oldAppCircleNoticeModels= noticeService.getNoticeModels(circle.getId());


        String result=mockMvc.perform(device.getApi("/circle/top")
                .param("id",""+circle.getId())
                .build())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()))
                .andReturn().getResponse().getContentAsString();
        JSONObject object=JSONObject.parseObject(result);
        JSONObject data=(JSONObject) object.get("resultData");
        JSONArray noticeList=(JSONArray) data.get("noticeList");
        JSONObject appCircleModel=(JSONObject) data.get("data");
        JSONArray top=(JSONArray) data.get("top");

        AppCircleNoticeModel[] newAppCircleNoticeModels=JSONObject.toJavaObject(noticeList, AppCircleNoticeModel[].class);
        AppCircleModel newappCircleModel=JSONObject.toJavaObject(appCircleModel, AppCircleModel.class);
        AppCircleArticleModel[] newAppCircleArticleModels=JSONObject.toJavaObject(top,AppCircleArticleModel[].class);
        assertEquals("appCircleModel",oldAppCircleModel,newappCircleModel);
        assertEquals("noticeList",oldAppCircleNoticeModels,newAppCircleNoticeModels);
        assertEquals("top",Arrays.asList(newAppCircleArticleModels),oldAppCircleArticleModels);



    }

    @Test
    public void list() throws Exception {
        Circle circle=new Circle();
        circle.setEnabled(true);
        circle.setDate(new Date());
        circle.setName("sltCircle");
        circle=circleRepository.saveAndFlush(circle);

        User user=new User();
        user.setId(10000L);

        Level level=new Level();
        level.setCustomerId(customerId);
        level.setName("level1");
        level.setExperience(20L);
        level=levelRepository.saveAndFlush(level);

        user.setLevel(level);
        user.setCustomerId(customerId);
        user.setNickName("testuserslt");
        user=userRepository.saveAndFlush(user);

        List<Article> articles=new ArrayList<>();
        Random random=new Random();
        for(int i=0;i<20;i++){
            Article article=new Article();
            article.setEnabled(true);
            article.setCircle(circle);
            article.setPublisher(user);
            article.setView(random.nextInt(i+1000));
            article.setDate(new Date(System.currentTimeMillis()+10000));
            articles.add(articleRepository.saveAndFlush(article));
        }

        Collections.sort(articles, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                return o2.getId().intValue()-o1.getId().intValue();
            }
        });

        List<Article> newArticles=articles;




        String result=mockMvc.perform(device.getApi("/circle/list")
                .param("id",""+circle.getId())
                .param("type","0")
                .build())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()))
                .andReturn().getResponse().getContentAsString();

        JSONObject object=JSONObject.parseObject(result);
        JSONObject data=(JSONObject) object.get("resultData");
        JSONArray articleList=(JSONArray) data.get("articleList");
        AppCircleArticleModel[] newArticleModels=JSONObject.toJavaObject(articleList,AppCircleArticleModel[].class);

        AppCircleArticleModel[] oldArticleModels=new AppCircleArticleModel[newArticles.size()];
        for(int i=0;i<newArticles.size();i++){
            oldArticleModels[i]=articleService.getAppCircleArticleModel(newArticles.get(i),null);
            oldArticleModels[i].setConcerned(false);
        }

        assertEquals("articleListNew",newArticleModels,oldArticleModels);

        Collections.sort(articles, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                return (int)(o2.getView()-o1.getView());
            }
        });

        List<Article> hotArticles=articles;

        result=mockMvc.perform(device.getApi("/circle/list")
                .param("id",""+circle.getId())
                .param("type","1")
                .build())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getValue()))
                .andReturn().getResponse().getContentAsString();

        object=JSONObject.parseObject(result);
        data=(JSONObject) object.get("resultData");
        articleList=(JSONArray) data.get("articleList");
        newArticleModels=JSONObject.toJavaObject(articleList,AppCircleArticleModel[].class);

        oldArticleModels=new AppCircleArticleModel[hotArticles.size()];
        for(int i=0;i<newArticles.size();i++){
            oldArticleModels[i]=articleService.getAppCircleArticleModel(newArticles.get(i),null);
            oldArticleModels[i].setConcerned(false);
        }
        assertEquals("articleListHot",newArticleModels,oldArticleModels);
    }

    @Test
    public void article() throws Exception {

    }

    @Test
    public void articleCommentsTop() throws Exception {

    }

    @Test
    public void articleCommentsList() throws Exception {

    }

}