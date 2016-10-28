package com.huotu.huobanplus.sns.entity;

import com.huotu.huobanplus.sns.model.common.CodeType;
import com.huotu.huobanplus.sns.model.common.VerificationType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 验证码
 */
@Entity
@Cacheable(value = false)
@Getter
@Setter
@Table(indexes = {@Index(columnList = "mobile"),@Index(columnList = "sendTime")})
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 11)
    private String mobile;

    /**
     * 商家
     */
    private Long customerId;


    @Column(nullable = false)
    private VerificationType type;

    private CodeType codeType;

    @Column(nullable = false,length = 8)
    private String code;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date sendTime;

}