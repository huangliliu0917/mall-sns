package com.huotu.huobanplus.sns.repository;

import com.huotu.huobanplus.sns.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/10/14.
 */
@Repository
public interface TagRespository extends JpaRepository<Tag, Integer> {
    Page<Tag> findByNameLike(String name, Pageable pageable);
}
