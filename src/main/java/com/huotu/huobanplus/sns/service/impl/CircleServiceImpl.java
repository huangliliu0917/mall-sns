/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service.impl;

import com.huotu.huobanplus.sns.entity.Category;
import com.huotu.huobanplus.sns.entity.Circle;
import com.huotu.huobanplus.sns.entity.User;
import com.huotu.huobanplus.sns.model.admin.CircleListModel;
import com.huotu.huobanplus.sns.model.admin.CircleSearchModel;
import com.huotu.huobanplus.sns.repository.CategoryRepository;
import com.huotu.huobanplus.sns.repository.CircleRepository;
import com.huotu.huobanplus.sns.repository.TagRespository;
import com.huotu.huobanplus.sns.repository.UserRepository;
import com.huotu.huobanplus.sns.service.CircleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by slt on 2016/10/12.
 */
@Service
public class CircleServiceImpl implements CircleService {
    @Autowired
    private CircleRepository circleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRespository tagRespository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<CircleListModel> findCircleListModel(List<Circle> circles) {
        List<CircleListModel> circleListModels = new ArrayList<>();
        if (circles == null || circles.isEmpty()) {
            return circleListModels;
        }
        circles.forEach(circle -> {
            circleListModels.add(circleToCircleModel(circle));
        });
        return circleListModels;
    }

    @Override
    public CircleListModel circleToCircleModel(Circle circle) {
        CircleListModel model = new CircleListModel();
        if (circle == null) {
            return model;
        }
        model.setCircleId(circle.getId());
        model.setCircleName(circle.getName());
        model.setPictureUrl(circle.getPictureUrl());
        model.setCategoryName(circle.getCategory() == null ? "" : circle.getCategory().getName());
        model.setLeaderName(circle.getLeader() == null ? "" : circle.getLeader().getNickName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        String createDate = dateFormat.format(circle.getDate());
        model.setCreateDate(createDate);
        model.setSuggested(circle.isSuggested());
        model.setArticleAmount(circle.getArticleAmount());
        model.setUserAmount(circle.getUserAmount());
        return model;
    }

    @Override
    public CircleListModel circleToDetailsCircleModel(Circle circle) {
        CircleListModel model=new CircleListModel();
        if(circle==null){
            return model;
        }
        model.setCircleId(circle.getId());
        model.setSummary(circle.getSummary());
        model.setCircleName(circle.getName());
        model.setPictureUrl(circle.getPictureUrl());
        model.setCategoryId(circle.getCategory()==null?-1:circle.getCategory().getId());
        model.setCategoryName(circle.getCategory()==null?"":circle.getCategory().getName());
        model.setLeaderId(circle.getLeader()==null?-1:circle.getLeader().getId());
        model.setLeaderName(circle.getLeader()==null?"":circle.getLeader().getNickName());
        model.setSuggested(circle.isSuggested());
        model.setTags(circle.getTags());
        return model;
    }

    @Override
    public Page<Circle> findCircleList(CircleSearchModel searchModel) throws IOException {
        Sort sort;
        if (StringUtils.isEmpty(searchModel.getSortField())) {
            sort = new Sort(Sort.Direction.DESC, "date", "suggested");
        } else {
            Sort.Direction sd = searchModel.getAscOrdesc() == 0 ? Sort.Direction.DESC : Sort.Direction.ASC;
            sort = new Sort(sd, searchModel.getSortField());
        }
        Specification<Circle> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.isEmpty(searchModel.getCircleName())) {
                predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + searchModel.getCircleName() + "%"));
            }
            if (searchModel.getSuggested() != -1) {
                boolean suggested = searchModel.getSuggested() == 1;
                predicates.add(criteriaBuilder.equal(root.get("suggested").as(Boolean.class), suggested));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Page<Circle> circleList = circleRepository.findAll(
                specification, new PageRequest(searchModel.getPageNo(), searchModel.getPageSize(), sort));

        return circleList;
    }

    @Override
    public void addCircle(CircleListModel circleListModel) throws IOException {
        Category category= categoryRepository.findOne(circleListModel.getCategoryId());
        User leader=userRepository.findOne(circleListModel.getLeaderId());
        Circle circle=new Circle();
        circle.setCategory(category);
        circle.setLeader(leader);
        circle.setName(circleListModel.getCircleName());
        circle.setPictureUrl(circleListModel.getPictureUrl());
        circle.setSummary(circleListModel.getSummary());
        circle.setDate(new Date());
        circle.setTags(circleListModel.getTags());
        circleRepository.save(circle);
    }

    @Override
    public void updateCircle(CircleListModel circleListModel) throws IOException {
        User leader=userRepository.findOne(circleListModel.getLeaderId());
        Category category= categoryRepository.findOne(circleListModel.getCategoryId());
        Circle circle=circleRepository.findOne(circleListModel.getCircleId());
        circle.setName(circleListModel.getCircleName());
        circle.setSummary(circleListModel.getSummary());
        circle.setSuggested(circleListModel.isSuggested());
        circle.setLeader(leader);
        circle.setCategory(category);
        circle.setPictureUrl(circleListModel.getPictureUrl());
        circle.setTags(circleListModel.getTags());
        circleRepository.save(circle);
    }

    @Override
    public List<Circle> findBySuggested(Boolean suggested) throws IOException {
        return circleRepository.findAll((root, query, cb) -> {
            Predicate predicate = cb.and(cb.isTrue(root.get("enabled")));
            if (Objects.nonNull(suggested))
                predicate = cb.and(predicate, cb.equal(root.get("suggested"), suggested));
            return predicate;
        }, new Sort(Sort.Direction.DESC, "date"));
    }
}
