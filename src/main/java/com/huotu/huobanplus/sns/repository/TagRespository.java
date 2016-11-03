package com.huotu.huobanplus.sns.repository;

import com.huotu.huobanplus.sns.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/10/14.
 */
@Repository
public interface TagRespository extends JpaRepository<Tag, Integer> {

    @Query("select tag from Tag tag where tag.customerId=?1 and tag.name=?2")
    Page<Tag> findByCustomerIdAndNameLike(Long customerId, String name, Pageable pageable);

    @Query("select tag from Tag tag where tag.customerId=?1")
    Page<Tag> findByCustomerId(Long customerId, Pageable pageable);
}
