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
     * 帖子ID
     */
    private Long invitationId;

    /**
     * 帖子内容
     */
    private String invitationContent;

    /**
     * 帖子是否可用
     */
    private boolean invitationEnabled;

    /**
     * 评论ID
     */
    private Long commentId;

    /**
     * 评论内容
     */
    private String comment;

    /**
     * 评论状态，0：正常，1：删除
     */
    private Integer commentStatus;

    /**
     * 举报类型
     */
    private ReportTargetType reportTargetType;

    /**
     * 是否拥有发帖权限
     */
    private boolean Posting;

    /**
     * 是否拥有发言权限
     */
    private boolean speak;

}
