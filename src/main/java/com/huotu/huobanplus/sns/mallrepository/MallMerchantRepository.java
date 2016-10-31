package com.huotu.huobanplus.sns.mallrepository;

import com.huotu.huobanplus.sns.mallentity.MallMerchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by Administrator on 2016/10/31.
 */
public interface MallMerchantRepository extends JpaRepository<MallMerchant,Long> {

    @Query("select m.subDomain from MallMerchant as m where m.id=?1")
    String findSubDomainByMerchantId(Long merchantId);
}
