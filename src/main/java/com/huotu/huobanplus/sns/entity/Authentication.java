package com.huotu.huobanplus.sns.entity;

import com.huotu.huobanplus.sns.model.common.AuthenticationType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 身份
 * Created by Administrator on 2016/10/8.
 */
@Entity
@Cacheable(value = false)
@Getter
@Setter
public class Authentication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 名称
     */
    @Column(length = 20)
    private String name;

    /**
     * 身份类型
     */
    private AuthenticationType type;
}
