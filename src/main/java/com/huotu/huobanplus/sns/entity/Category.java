package com.huotu.huobanplus.sns.entity;

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

}
