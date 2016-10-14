package com.huotu.huobanplus.sns.service.impl;

import com.huotu.huobanplus.sns.entity.Circle;
import com.huotu.huobanplus.sns.model.admin.CircleListModel;
import com.huotu.huobanplus.sns.model.admin.CircleSearchModel;
import com.huotu.huobanplus.sns.repository.CircleRepository;
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
import java.util.List;

/**
 * Created by slt on 2016/10/12.
 */
@Service
public class CircleServiceImpl implements CircleService {
    @Autowired
    private CircleRepository circleRepository;


    @Override
    public List<CircleListModel> findCircleListModel(List<Circle> circles) {
        List<CircleListModel> circleListModels=new ArrayList<>();
        if(circles==null||circles.isEmpty()){
            return circleListModels;
        }
        circles.forEach(circle -> {
            circleListModels.add(circleToCircleModel(circle));
        });
        return circleListModels;
    }

    @Override
    public CircleListModel circleToCircleModel(Circle circle) {
        CircleListModel model=new CircleListModel();
        if(circle==null){
            return model;
        }
        model.setCircleId(circle.getId());
        model.setCircleName(circle.getName());
        model.setPictureUrl(circle.getPictureUrl());
        model.setCategoryName(circle.getCategory()==null?"":circle.getCategory().getName());
        model.setLeaderName(circle.getLeader()==null?"":circle.getLeader().getNickName());
        SimpleDateFormat dateFormat=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        String createDate=dateFormat.format(circle.getDate());
        model.setCreateDate(createDate);
        model.setSuggested(circle.isSuggested()?"热门":"普通");
        model.setArticleAmount(circle.getArticleAmount());
        model.setUserAmount(circle.getUserAmount());
        return model;
    }

    @Override
    public Page<Circle> findCircleList(CircleSearchModel searchModel) throws IOException {
        Sort sort;
        if(StringUtils.isEmpty(searchModel.getSortField())){
            sort=new Sort(Sort.Direction.DESC,"date","suggested");
        }else {
            Sort.Direction sd=searchModel.getAscOrdesc()==0?Sort.Direction.DESC: Sort.Direction.ASC;
            sort=new Sort(sd,searchModel.getSortField());
        }
        Specification<Circle> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!StringUtils.isEmpty(searchModel.getCircleName())){
                predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+searchModel.getCircleName()+"%"));
            }
            if(searchModel.getSuggested()!=-1){
                boolean suggested=searchModel.getSuggested()==1;
                predicates.add(criteriaBuilder.equal(root.get("suggested").as(Boolean.class), suggested));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        Page<Circle> circleList=circleRepository.findAll(
                specification,new PageRequest(searchModel.getPageNo(),searchModel.getPageSize(),sort));

        return circleList;
    }
}
