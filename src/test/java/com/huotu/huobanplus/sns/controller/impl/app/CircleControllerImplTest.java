package com.huotu.huobanplus.sns.controller.impl.app;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huotu.huobanplus.sns.CommonTestBase;
import com.huotu.huobanplus.sns.entity.*;
import com.huotu.huobanplus.sns.model.AppCircleIndexArticleListModel;
import com.huotu.huobanplus.sns.model.AppCircleIndexListModel;
import com.huotu.huobanplus.sns.model.AppCircleIndexSlideModel;
import com.huotu.huobanplus.sns.model.AppCircleIndexSuggestModel;
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
            slide.setCustomerId(3447L);
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
            circle.setCustomerId(3447L);
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
            circle.setCustomerId(3447L);
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
                .param("pageNo","0")
                .param("pageSize","12")
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
        puser.setCustomerId(3447L);
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
                article.setCustomerId(3447L);
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
            userCircle.setCustomerId(3447L);
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

    }

    @Test
    public void list() throws Exception {

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