/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.repository;

import com.huotu.huobanplus.sns.entity.Slide;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by slt on 2016/10/10.
 */
@Repository
public interface SlideRepository extends JpaRepository<Slide, Long>, JpaSpecificationExecutor<Slide> {

    List<Slide> findByCustomerIdOrderByIdDesc(Long id);
    
    List<Slide> findByCustomerId(Long id, Pageable pageable);
}
