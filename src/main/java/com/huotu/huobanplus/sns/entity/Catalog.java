package com.huotu.huobanplus.sns.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 科普分类
 * Created by Administrator on 2016/9/29.
 */
@Entity
@Getter
@Setter
@Cacheable(value = false)
public class Catalog extends AbstractCategory {

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private Catalog parent;


}
