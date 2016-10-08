package com.huotu.huobanplus.sns.entity;

import com.huotu.huobanplus.sns.model.common.CategoryType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 文章分类
 */
@Entity
@Getter
@Setter
@Cacheable(value = false)
public class Category extends AbstractCategory {

    /**
     * 所属父分类
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private Category parent;

    /**
     * 分类类型
     */
    private CategoryType categoryType;

}
