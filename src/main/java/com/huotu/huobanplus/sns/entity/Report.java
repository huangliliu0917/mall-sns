package com.huotu.huobanplus.sns.entity;

import com.huotu.huobanplus.sns.model.common.ReportTargetType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 举报
 * Created by Administrator on 2016/10/8.
 */
@Entity
@Cacheable(value = false)
@Getter
@Setter
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 举报者
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private User user;

    /**
     * 举报目标类型
     */
    private ReportTargetType reportTargetType;

    /**
     * 被举报的目标
     * 文章，回复，人
     */
    private Long targetId;

    /**
     * 说明
     */
    @Column(length = 200)
    private String content;

    /**
     * 日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
}
