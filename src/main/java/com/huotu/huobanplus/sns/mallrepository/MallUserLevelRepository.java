package com.huotu.huobanplus.sns.mallrepository;

import com.huotu.huobanplus.sns.mallentity.MallUserLevel;
import com.huotu.huobanplus.sns.model.common.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2016/11/1.
 */
public interface MallUserLevelRepository extends JpaRepository<MallUserLevel, Long> {

    @Query("select level from MallUserLevel level where level.customerId=?1 and level.type=?2")
    Page<MallUserLevel> findByCustomerIdAndTypeMin(Long customerId, UserType userType, Pageable pageable);
}
