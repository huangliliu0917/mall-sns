/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service.impl;

import com.huotu.huobanplus.sns.entity.Article;
import com.huotu.huobanplus.sns.entity.Circle;
import com.huotu.huobanplus.sns.entity.User;
import com.huotu.huobanplus.sns.entity.UserCircle;
import com.huotu.huobanplus.sns.exception.ConcernException;
import com.huotu.huobanplus.sns.exception.NeedLoginException;
import com.huotu.huobanplus.sns.model.AppCircleIndexArticleListModel;
import com.huotu.huobanplus.sns.model.AppCircleIndexListModel;
import com.huotu.huobanplus.sns.model.common.AppCode;
import com.huotu.huobanplus.sns.repository.ArticleRepository;
import com.huotu.huobanplus.sns.repository.CircleRepository;
import com.huotu.huobanplus.sns.repository.UserCircleRepository;
import com.huotu.huobanplus.sns.service.CommonConfigService;
import com.huotu.huobanplus.sns.service.UserCircleService;
import com.huotu.huobanplus.sns.utils.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 用户圈子实现
 * Created by jin on 2016/10/10.
 */
@Service
public class UserCircleServiceImpl implements UserCircleService {
    @Autowired
    private CircleRepository circleRepository;
    @Autowired
    private UserCircleRepository userCircleRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommonConfigService commonConfigService;

    @Transactional
    @Override
    public void concern(Long id) throws ConcernException, NeedLoginException, IOException {
        User user = UserHelper.getUser();
        Circle circle = circleRepository.getOne(id);
        List<UserCircle> userCircles = userCircleRepository.findByUserAndCircle(user, circle);
        if (Objects.nonNull(userCircles) && userCircles.size() > 0)
            throw new ConcernException(AppCode.ERROR_CONCERN_ALREADY.getValue(), AppCode.ERROR_CONCERN_ALREADY.getName());
        UserCircle userCircle = new UserCircle();
        userCircle.setCustomerId(user.getCustomerId());
        userCircle.setCircle(circle);
        userCircle.setDate(new Date());
        userCircle.setUser(user);
        userCircleRepository.save(userCircle);
//        BoundHashOperations<String, String, Long> circleOperations = redisTemplate
//                .boundHashOps(ContractHelper.circleFlag + id);
//        circleOperations.putIfAbsent("userAmount", 0L);
//        synchronized (circleOperations.get("userAmount")) {
//            Long userAmount = circleOperations.get("userAmount");
//            circleOperations.put("userAmount", userAmount + 1L);
//        }
        circleRepository.addUserAmount(id);
    }

    @Transactional
    @Override
    public void cancelConcern(Long id) throws ConcernException, NeedLoginException, IOException {
        User user = UserHelper.getUser();
        Circle circle = circleRepository.getOne(id);
        List<UserCircle> userCircles = userCircleRepository.findByUserAndCircle(user, circle);
        if (Objects.isNull(userCircles) || userCircles.size() == 0)
            throw new ConcernException(AppCode.ERROR_UNCONCERN_ALREADY.getValue(), AppCode.ERROR_UNCONCERN_ALREADY.getName());
        for (UserCircle userCircle : userCircles) {
            userCircleRepository.delete(userCircle);
        }
        circleRepository.reduceUserAmount(id);
//        BoundHashOperations<String, String, Long> circleOperations = redisTemplate
//                .boundHashOps(ContractHelper.circleFlag + id);
//        synchronized (circleOperations.get("userAmount")) {
//            Long userAmount = circleOperations.get("userAmount");
//            circleOperations.put("userAmount", userAmount - 1L);
//        }
    }

    @Override
    public List<UserCircle> getUserCircleList(Long customerId,Long userId, Long lastId) throws IOException {
        if(lastId==null){
            return userCircleRepository.findTop5ByCustomerIdAndUser_IdOrderByIdDesc(customerId,userId,new PageRequest(0,5));
        }else {
            return userCircleRepository.findTop5ByCustomerIdAndUser_IdAndIdLessThanOrderByIdDesc(customerId,userId,lastId,new PageRequest(0,5));

        }
    }

    @Override
    public AppCircleIndexListModel[] getCircleIndexListModelList(List<UserCircle> userCircles) throws IOException {
        AppCircleIndexListModel[] models=new AppCircleIndexListModel[userCircles.size()];
        for(int i=0,size=userCircles.size();i<size;i++){
            if(!userCircles.get(i).getCircle().isEnabled()){
                continue;
            }
            models[i]=getCircleIndexListModel(userCircles.get(i));
        }
        return models;
    }

    @Override
    public AppCircleIndexListModel getCircleIndexListModel(UserCircle userCircle) throws IOException {
        AppCircleIndexListModel model=new AppCircleIndexListModel();
        model.setPid(userCircle.getId());
        model.setCircleId(userCircle.getCircle().getId());
        model.setName(userCircle.getCircle().getName());
        String circlePictureUrl=userCircle.getCircle().getPictureUrl();
        model.setPictureUrl(circlePictureUrl);
        model.setNum(userCircle.getCircle().getUserAmount());
        model.setIntroduceUrl(commonConfigService.getWebUrl()+"/app/circle/introduce?id="+userCircle.getCircle().getId());//todo 圈子介绍链接地址
        model.setUrl(commonConfigService.getWebUrl()+"/app/circle/list?id="+userCircle.getCircle().getId());//todo 圈子页面

        List<Article> circles=articleRepository.findTop3ByCircle_IdAndEnabledOrderByIdDesc(userCircle.getCircle().getId(),true,new PageRequest(0,3));

        List<AppCircleIndexArticleListModel> articleModels=getArticleModelList(circles);
        model.setList(articleModels);

        return model;
    }

    @Override
    public AppCircleIndexArticleListModel getArticleModel(Article article) {
        AppCircleIndexArticleListModel model=new AppCircleIndexArticleListModel();
        model.setArticleId(article.getId());
        model.setName(article.getName());
//        model.setUrl(""+article.getId());//todo 跳转地址
        model.setUserName(article.getPublisher().getNickName());
        model.setViewAmount(article.getView());
        model.setCommentsAmount(article.getComments());
        return model;
    }

    @Override
    public List<AppCircleIndexArticleListModel> getArticleModelList(List<Article> articles) {
        List<AppCircleIndexArticleListModel> models=new ArrayList<>();
        if(articles==null){
            return models;
        }
        articles.forEach(article -> {
            models.add(getArticleModel(article));
        });
        return models;
    }
}
