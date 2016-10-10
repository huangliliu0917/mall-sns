package com.huotu.huobanplus.sns.entity;

import com.huotu.huobanplus.sns.model.common.CommentStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/29.
 */

@MappedSuperclass
@Getter
@Setter
public class AbstractComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商家
     */
    private Long customerId;

    /**
     * 评论人
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private User user;

    /**
     * 评论内容
     */
    @Column(length = 400)
    private String content;

    /**
     * 评论时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /**
     * 点赞量
     */
    private Long click;

    /**
     * 所属评论(评论的评论)
     */
    @ManyToOne
    private ArticleComment articleComment;

    /**
     * 扩展信息 (用于评论的回复信息显示)
     * 评论的评论内容无限循环冗余 以json格式存储
     * {fromId:,id:,content:,date:,userName:,userPictureUrl:,data:[{fromId:,id:,content:,date:,userName:,userPictureUrl:}]}
     */
    @Lob
    private String extend;

    /**
     * 评论状态
     */
    private CommentStatus commentStatus;

    /**
     * 楼层
     */
    private Long floor;
}
