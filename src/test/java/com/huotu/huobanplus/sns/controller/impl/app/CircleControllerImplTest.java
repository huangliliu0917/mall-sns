package com.huotu.huobanplus.sns.controller.impl.app;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huotu.huobanplus.sns.CommonTestBase;
import com.huotu.huobanplus.sns.entity.Circle;
import com.huotu.huobanplus.sns.entity.Slide;
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
        AppCircleIndexSuggestModel[] oldCircleModels=circleService.getCircleAppModel(circles);

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
        AppCircleIndexSuggestModel[] oldCircleModels=circleService.getCircleAppModel(circles);

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