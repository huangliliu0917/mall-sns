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
import com.huotu.huobanplus.sns.entity.Category;
import com.huotu.huobanplus.sns.model.common.ArticleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by slt on 2016/10/9.
 */
@SuppressWarnings("JpaQlInspection")
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

    @Query("select article from Article article where article.articleType=?1")
    Page<Article> findByArticleType(ArticleType articleType, Pageable pageable);


    @Query("select article from Article article where article.articleType=?1 and article.id<?2 order by  article.id desc")
    List<Article> findByArticleType(ArticleType articleType, Long lastId);

    @Query("select article from Article article where article.articleType=?1 and article.category=?2")
    Page<Article> findByArticleTypeAndCategory(ArticleType articleType, Category category, Pageable pageable);


    @Query("select article from Article article where article.articleType=?1 and article.category=?2 and article.id<?3 order by  article.id desc")
    List<Article> findByArticleTypeAndCategory(ArticleType articleType, Category category, Long lastId);

    Page<Article> findByArticleTypeAndNameLike(ArticleType articleType, String name, Pageable pageable);

    @Query("update Article a set a.comments=a.comments+1 where a.id=?1")
    void addComments(@Param("articleId") Long articleId);
}
