/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.repository;

import com.huotu.huobanplus.sns.entity.Circle;
import com.huotu.huobanplus.sns.entity.User;
import com.huotu.huobanplus.sns.entity.UserCircle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jin on 2016/10/10.
 */
@Repository
public interface UserCircleRepository extends JpaRepository<UserCircle, Long>, JpaSpecificationExecutor<UserCircle> {

    List<UserCircle> findByUserAndCircle(@Param("user") User user, @Param("circle") Circle circle);

    List<UserCircle> findTop5ByCustomerIdAndUser_IdAndIdLessThanOrderByIdDesc(Long customerId,Long userId,Long id);

    List<UserCircle> findTop5ByCustomerIdAndUser_IdOrderByIdDesc(Long customerId,Long userId);
}
