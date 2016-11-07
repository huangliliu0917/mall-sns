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
import com.huotu.huobanplus.sns.entity.User;
import com.huotu.huobanplus.sns.model.common.ArticleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query("select article from Article article where article.customerId=?1 and article.articleType=?2")
    Page<Article> findByCustomerIdAndArticleType(Long customerId, ArticleType articleType, Pageable pageable);


    @Query("select article from Article article where article.articleType=?1 and article.id<?2 order by  article.id desc")
    List<Article> findByCustomerIdAndArticleType(ArticleType articleType, Long lastId);

    @Query("select article from Article article where article.articleType=?1 and article.category=?2")
    Page<Article> findByArticleTypeAndCategory(ArticleType articleType, Category category, Pageable pageable);


    @Query("select article from Article article where article.articleType=?1 and article.category=?2 and article.id<?3 order by  article.id desc")
    List<Article> findByArticleTypeAndCategory(ArticleType articleType, Category category, Long lastId);

    @Query("select article  from Article article where article.customerId=?1 and article.articleType=?2 and  article.name like ?3")
    Page<Article> findByCustomerIdAndArticleTypeAndNameLike(Long customerId, ArticleType articleType, String name, Pageable pageable);

    @Query("update Article a set a.comments=a.comments+1 where a.id=?1")
    void addComments(@Param("articleId") Long articleId);

    @Modifying(clearAutomatically = true)
    @Query("update Article a set a.click=a.click+1 where a.id=?1")
    void addClick(@Param("articleId") Long articleId);

    List<Article> findTop10ByPublisherOrderByIdDesc(@Param("publisher") User publisher);

    @Query("select article from Article article where article.customerId=?1 and article.name like ?2")
    Page<Article> findByCustomerIdAndNameLike(Long customerId, String name, Pageable pageable);

    @Query("select article from Article article where article.customerId=?1 and article.id<?2 and article.name like ?3")
    Page<Article> findByCustomerIdAndNameLike(Long customerId, String name, Long lastId, Pageable pageable);

    List<Article> findTop3ByCircle_IdAndEnabledOrderByIdDesc(Long circleId,boolean enabled);

    List<Article> findByTopAndCircle_IdAndEnabledOrderByIdDesc(boolean isTop,Long circleId,boolean enabled);

    List<Article> findTop20ByCircle_IdAndEnabledAndIdLessThanOrderByIdDesc(Long circleId,boolean enabled,Long lastId);

    List<Article> findTop20ByCircle_IdAndEnabledAndViewLessThanOrderByViewDesc(Long circleId,boolean enabled,Long view);
}
