package com.huotu.huobanplus.sns.repository;

import com.huotu.huobanplus.sns.entity.Category;
import com.huotu.huobanplus.sns.model.common.CategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Page<Category> findByCategoryType(CategoryType categoryType,Pageable pageable);

    Page<Category> findByCategoryTypeAndNameLike(CategoryType categoryType,String name,Pageable pageable);

    List<Category> findByParentAndCategoryType(Category parent, CategoryType categoryType);

    Page<Category> findByParentAndCategoryType(Category parent, CategoryType categoryType, Pageable pageable);

}
