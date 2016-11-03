package com.huotu.huobanplus.sns.repository;

import com.huotu.huobanplus.sns.entity.Category;
import com.huotu.huobanplus.sns.model.common.CategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/10/12.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("select category from Category  category where category.customerId=?1 and  category.categoryType=?2")
    Page<Category> findByCustomerIdAndCategoryType(Long customerId, CategoryType categoryType, Pageable pageable);

    @Query("select category from Category  category where category.customerId=?1 and  category.categoryType=?2 and category.name like ?3")
    Page<Category> findByCustomerIdCategoryTypeAndNameLike(Long customerId, CategoryType categoryType, String name, Pageable pageable);

    @Query("select category from Category  category where category.customerId=?1 and category.parent=?2 and category.categoryType=?3")
    List<Category> findByCustomerIdAndParentAndCategoryType(Long customerId, Category parent, CategoryType categoryType);


    @Query("select category from Category  category where category.customerId=?1 and  category.categoryType=?2")
    List<Category> findByCustomerIdAndCategoryType(Long customerId, CategoryType categoryType);

//    @Query("select category from Category category where category.parent=?1 and category.categoryType=?2")
//    Page<Category> findByCustomerIdAndParentAndCategoryType(Category parent, CategoryType categoryType, Pageable pageable);

}
