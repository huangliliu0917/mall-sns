/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service.impl;

import com.huotu.huobanplus.sns.entity.Login;
import com.huotu.huobanplus.sns.model.admin.PermissionSearchModel;
import com.huotu.huobanplus.sns.repository.LoginRepository;
import com.huotu.huobanplus.sns.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 权限服务实现(多商户下弃用)
 * Created by Administrator on 2016/10/9.
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private LoginRepository managerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<Login> getManagerList(PermissionSearchModel model) throws Exception {
        Sort sort;
        if (StringUtils.isEmpty(model.getSortField())) {
            sort = new Sort(Sort.Direction.DESC, "lastLoginDate");
        } else {
            Sort.Direction sd = model.getAscOrdesc() == 0 ? Sort.Direction.DESC : Sort.Direction.ASC;
            sort = new Sort(sd, model.getSortField());
        }
        Specification<Login> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.isEmpty(model.getName())) {
                predicates.add(criteriaBuilder.like(root.get("loginName").as(String.class), "%" + model.getName() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return managerRepository.findAll(specification,new PageRequest(model.getPageNo(),model.getPageSize(),sort));
    }

    @Override
    public void addPermission(Login login) throws Exception {
        login.setPassword(passwordEncoder.encode(login.getPassword()));
        login.setLastLoginDate(new Date());
        managerRepository.save(login);
    }

    @Override
    public void updatePermission(Login login) throws Exception {
        Login oldLogin=managerRepository.findOne(login.getId());
        login.setPassword(passwordEncoder.encode(login.getPassword()));
        login.setLastLoginDate(oldLogin.getLastLoginDate());
        login.setLastLoginIP(oldLogin.getLastLoginIP());
        login.setEnabled(login.isEnabled());
        managerRepository.save(login);
    }
}
