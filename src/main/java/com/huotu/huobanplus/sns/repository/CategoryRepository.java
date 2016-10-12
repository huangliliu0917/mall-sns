package com.huotu.huobanplus.sns.repository;

import com.huotu.huobanplus.sns.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    List<Category> findByParent(Category parent);


}
