/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.repository;

import com.huotu.huobanplus.sns.entity.Article;
import com.huotu.huobanplus.sns.entity.ArticleClick;
import com.huotu.huobanplus.sns.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by jin on 2016/11/2.
 */
@Repository
public interface ArticleClickRepository extends JpaRepository<ArticleClick, Long>, JpaSpecificationExecutor<ArticleClick> {

    ArticleClick findByArticleAndUser(@Param("article") Article article, @Param("user") User user);
}
