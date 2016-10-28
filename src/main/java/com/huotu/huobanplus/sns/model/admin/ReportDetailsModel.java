package com.huotu.huobanplus.sns.model.admin;

import com.huotu.huobanplus.sns.model.common.ReportTargetType;
import lombok.Getter;
import lombok.Setter;

/**
 * 举报详情model
 * Created by slt on 2016/10/12.
 */
@Setter
@Getter
public class ReportDetailsModel {
    /**
     * 举报者ID
     */
    private Long reportId;

    /**
     * 举报者姓名
     */
    private String reportName;

    /**
     * 被举报者ID
     */
    private Long reportedId;

    /**
     * 被举报者姓名
     */
    private String reportedName;

    /**
     * 说明
     */
    private String content;

    /**
     * 帖子标题
     */
    private String invitationTitle;

    /**
     * 帖子内容
     */
    private String invitationContent;

    /**
     * 评论内容
     */
    private String comment;

    /**
     * 类型
     */
    private ReportTargetType reportTargetType;

}
