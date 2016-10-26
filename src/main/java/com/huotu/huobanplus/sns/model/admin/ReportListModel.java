package com.huotu.huobanplus.sns.model.admin;

import lombok.Getter;
import lombok.Setter;

/**
 * 举报列表model
 * Created by slt on 2016/10/12.
 */
@Setter
@Getter
public class ReportListModel {

    /**
     * id
     */
    private Long reportId;

    /**
     * 举报者名称
     */
    private String reportName;

    /**
     * 举报类型
     */
    private String typeName;

    /**
     * 目标ID
     */
    private Long targetId;

    /**
     * 说明
     */
    private String content;

    /**
     * 创建时间
     */
    private String createDate;

}
