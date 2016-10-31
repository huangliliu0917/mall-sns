/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.repository;

import com.huotu.huobanplus.sns.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by jin on 2016/10/9.
 */
@SuppressWarnings("JpaQlInspection")
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Long countByLevelId(@Param("levelId") Long levelId);

    @Query("update User u set u.articleAmount=u.articleAmount+1 where u.id=?1")
    void addArticleAmount(@Param("userId") Long userId);

    @Query("update User u set u.userAmount=u.userAmount+1 where u.id=?1")
    void addUserAmount(@Param("userId") Long userId);

    @Query("update User u set u.fansAmount=u.fansAmount+1 where u.id=?1")
    void addFansAmount(@Param("userId") Long userId);

    @Query("update User u set u.userAmount=u.userAmount-1 where u.id=?1")
    void reduceUserAmount(@Param("userId") Long userId);

    @Query("update User u set u.fansAmount=u.fansAmount-1 where u.id=?1")
    void reduceFansAmount(@Param("userId") Long userId);

    @Query("select user from User user where user.customerId=?1 and user.openId=?2")
    User findByCustomerIdAndOpenId(Long customerId, String openId);
}
