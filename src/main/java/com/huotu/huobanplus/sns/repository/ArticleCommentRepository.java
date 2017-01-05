/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.repository;

import com.huotu.huobanplus.sns.entity.ArticleComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by jin on 2016/10/20.
 */
@Repository
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long>, JpaSpecificationExecutor<ArticleComment> {

    @Query("select max(a.floor) from ArticleComment a where a.article.id=?1")
    Optional<Long> getMaxFloorByArticleId(@Param("articleId") Long articleId);

    //    @Query("select ac from ArticleComment as ac where ac.article.id=?1 and ac.customerId=?2")
    Page<ArticleComment> findByArticle_IdAndCustomerId(Long articleId, Long customerId, Pageable pageable);

    List<ArticleComment> findByPathLike(@Param("path") String path);

    List<ArticleComment> findByArticleId(Long articleId, Sort sort);

    @Query("select a.path from ArticleComment a where a.id=?1")
    String findPathByArticleCommentId(@Param("id") Long id);


}
