package com.huotu.huobanplus.sns.mallentity;

import com.huotu.huobanplus.sns.model.common.UserType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.Description;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/11/1.
 */
@Entity
@Setter
@Getter
@Cacheable(value = false)
@Table(name = "Mall_UserLevel")
@Description("会员等级")
public class MallUserLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UL_ID")
    private Long id;

    @Description("等级")
    @Column(name = "UL_Level")
    private int level;
    @Description("等级描述")
    @Column(name = "UL_LevelName", length = 50)
    private String levelName;
    @Description("等级类型,何物")
    @Column(name = "UL_Type")
    private UserType type;
    /**
     * 商家
     */
    @Column(name = "UL_CustomerID")
    private Long customerId;
    @Lob
    @Description("等级描述")
    @Column(name = "UL_Description")
    private String description;
    @Description("默认等级")
    @Column(name = "UL_DefaultLevel")
    private int defaultLevel;
    @Description("对应返利积分")
    @Column(name = "UL_Integral")
    private int integral;
    @Description("对应会员数量")
    @Column(name = "UL_MemberNum")
    private int memberNumber;
    @Description("对应直接团队数量")
    @Column(name = "UL_DirectTeamNum")
    private int directTeamNumber;
    @Description("对应间接团队数量")
    @Column(name = "UL_IndirectTeamNum")
    private int indirectTeamNumber;
    @Description("对应消费金额")
    @Column(name = "UL_Money")
    private double money;
}
