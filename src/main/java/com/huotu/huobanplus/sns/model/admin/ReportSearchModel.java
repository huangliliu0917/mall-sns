package com.huotu.huobanplus.sns.model.admin;

import lombok.Getter;
import lombok.Setter;

/**
 * 后台举报用户
 * Created by slt on 2016/10/12.
 */
@Setter
@Getter
public class ReportSearchModel {

    /**
     * 商户ID
     */
    private Long customerId;

    /**
     * 用户名称
     */
    private String name="";

    /**
     * 类型
     */
    private String reportTargetName;

    /**
     * 第几页
     */
    private int pageNo=0;

    /**
     * 每页数量
     */
    private int pageSize=10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 0:倒序，1：顺序
     */
    private int ascOrdesc;
}
