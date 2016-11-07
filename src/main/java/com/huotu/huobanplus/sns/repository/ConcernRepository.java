/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.repository;

import com.huotu.huobanplus.sns.entity.Concern;
import com.huotu.huobanplus.sns.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by jin on 2016/10/10.
 */
@Repository
public interface ConcernRepository extends JpaRepository<Concern, Long>, JpaSpecificationExecutor<Concern> {

    /**
     * 查询user关注toUser的列表
     *
     * @param user   关注用户
     * @param toUser 被关注用户
     * @return
     */
    List<Concern> findByUserAndToUser(@Param("user") User user, @Param("toUser") User toUser);

    /**
     * 查询关注了toUserId的用户列表信息
     *
     * @param toUserId 被关注用户id
     * @return
     */
    List<Concern> findByToUserId(@Param("toUserId") Long toUserId);

    /**
     * 查询关注了toUser的用户列表信息
     *
     * @param toUser 被关注用户
     * @return
     */
    List<Concern> findByToUser(@Param("toUser") User toUser);

    /**
     * 查询粉丝列表,前10位
     *
     * @param toUser
     * @return
     */
    List<Concern> findTop10ByToUserOrderByIdDesc(@Param("toUser") User toUser);

    /**
     * 查询主键小于id的粉丝列表,前10位
     *
     * @param toUser
     * @return
     */
    List<Concern> findTop10ByToUserAndIdLessThanOrderByIdDesc(@Param("toUser") User toUser, @Param("id") Long id);

    /**
     * 查询关注列表,前10位
     *
     * @param user
     * @return
     */
    List<Concern> findTop10ByUserOrderByIdDesc(@Param("user") User user);

    /**
     * 查询主键小于id的关注列表,前10位
     *
     * @param user
     * @param id
     * @return
     */
    List<Concern> findTop10ByUserAndIdLessThanOrderByIdDesc(@Param("user") User user, @Param("id") Long id);

    List<Concern> findByUser(@Param("user") User user);


    /**
     * 根据给定的用户获取自己是否关注了这些用户
     * @param userId        自己ID
     * @param customerId    商户ID
     * @param toUserIds     给定用户列表
     * @return              关注列表
     */
    @Query("select c from Concern as c where c.user.id=?1 and c.customerId=?2 and c.toUser.id in ?3")
    List<Concern> findByUserAndToUsers(Long userId,Long customerId, Set<Long> toUserIds);
}
