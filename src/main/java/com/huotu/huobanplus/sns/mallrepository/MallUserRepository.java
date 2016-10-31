package com.huotu.huobanplus.sns.mallrepository;

import com.huotu.huobanplus.sns.mallentity.MallUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/10/31.
 */
@Repository
public interface MallUserRepository extends JpaRepository<MallUser, Long> {

    @Query("select user from MallUser user where user.customerId=?1 and user.mobile=?2")
    MallUser findByCustomerIdAndMobile(Long customerId, String phone);
}
