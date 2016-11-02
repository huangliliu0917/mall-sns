/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.repository;

import com.huotu.huobanplus.sns.entity.UserArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by jin on 2016/10/19.
 */
@Repository
public interface UserArticleRepository extends JpaRepository<UserArticle, Long>, JpaSpecificationExecutor<UserArticle> {

    /**
     * 查询文章列表
     *
     * @param ownerId 收到文章的用户id
     * @return
     */
    List<UserArticle> findTop10ByOwnerIdOrderByIdDesc(@Param("ownerId") Long ownerId);

    /**
     * 查询主键小于id的文章列表,前10位
     *
     * @param ownerId 收到文章的用户id
     * @param id
     * @return
     */
    List<UserArticle> findTop10ByOwnerIdAndIdLessThanOrderByIdDesc(@Param("ownerId") Long ownerId, @Param("id") Long id);

    Optional<Long> countByArticleId(@Param("articleId") Long articleId);
}
