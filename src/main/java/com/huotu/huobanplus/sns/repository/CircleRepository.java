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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.IOException;

/**
 * Created by jin on 2016/10/10.
 */
@SuppressWarnings("JpaQlInspection")
@Repository
public interface CircleRepository extends JpaRepository<Circle, Long>, JpaSpecificationExecutor<Circle> {

    @Modifying(clearAutomatically = true)
    @Query("update Circle c set c.suggested=:suggested where c.id = :id")
    void updateSuggest(@Param("suggested") boolean suggested, @Param("id") Long id) throws IOException;

    @Query("update Circle c set c.articleAmount=c.articleAmount+1 where c.id = :id")
    void addArticleAmount(@Param("id") Long id);

    @Query("update Circle c set c.userAmount=c.userAmount+1 where c.id = :id")
    void addUserAmount(@Param("id") Long id);

    @Query("update Circle c set c.userAmount=c.userAmount-1 where c.id = :id")
    void reduceUserAmount(@Param("id") Long id);
}
