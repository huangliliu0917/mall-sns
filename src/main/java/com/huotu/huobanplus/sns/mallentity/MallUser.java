package com.huotu.huobanplus.sns.mallentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.huotu.huobanplus.sns.model.common.UserType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.Description;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/31.
 */
@Entity
@Cacheable(value = false)
@Getter
@Setter
@Table(name = "Hot_UserBaseInfo")
@Description("用户")
public class MallUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UB_UserID")
    @Description("会员号")
    private Long id;

    /**
     * 商家
     */
    @Column(name = "UB_CustomerID")
    @Description("所在商户")
    private Long customerId;

    /**
     * 登录名
     */
    @Column(name = "UB_UserLoginName", nullable = false, length = 50)
    @Description("登录名")
    private String loginName;

    /**
     * 密码
     * 保存的是一次md5以后的小写hex
     */
    @Column(name = "UB_UserLoginPassword", length = 32)
    @JsonIgnore
    private String password;


    /**
     * 昵称
     */
    @Column(name = "UB_UserNickName", length = 100)
    @Description("昵称")
    private String nickName;


    /**
     * 微信头像地址
     *
     * @since 1.3
     */
    @Column(name = "UB_WxHeadImg")
    @Description("微信头像地址")
    private String weixinImageUrl;

    /**
     * 真实姓名
     */
    @Column(name = "UB_UserRealName", length = 100)
    @Description("真实姓名")
    private String realName;

    /**
     * 手机号
     */
    @Column(name = "UB_UserMobile", length = 50)
    @Description("手机号")
    private String mobile;
    //改成0(1->待绑定；->已绑定)
    /**
     * 是否需要绑定手机,绑定手机需要设置为false;默认为true
     *
     * @since 1.3
     */
    @Column(name = "UB_MobileToBeBind")
    private boolean mobileBindRequired = true;

    /**
     * 0:普通会员 1:小伙伴
     */
    @Column(name = "UB_UserType")
    @Description("用户类型")
    private UserType userType;
    /**
     * 关联至Mall_UserLevel
     * 也就是UserLevel的主键,因为看到了大量的..无效连接所以这里不设置关联.
     */
    @Column(name = "UB_LevelID")
    @Description("具体级别id")
    private long levelId;



    @Column(name = "UB_WxNickName")
    @Description("微信昵称")
    private String wxNickName;

    @OneToOne(orphanRemoval = true, mappedBy = "userInfo", cascade = CascadeType.ALL)
    @Description("微信绑定信息")
    private MallUserBinding binding;

    @Column(name = "UB_UserIntegral")

    private Long userIntegral;

    @Column(name = "UB_BelongOne")
    @Description("上线")
    private Long belongOne;


    @Column(name = "UB_BelongTwo")
    @Description("上上线")
    private Long belongTwo;

    @Column(name = "UB_BelongThree")
    @Description("上上上线")
    private Long belongThree;

    @Column(name = "UB_UserBalance")
    @Description("余额")
    private Double userBalance;

    @Column(name = "UB_UserTempIntegral")
    @Description("临时积分")
    private Integer userTempIntegral;
    /**
     * 注册时间
     *
     * @since 1.5.3
     */
    @Column(name = "UB_UserRegTime")
    @Description("注册时间")
    @Temporal(TemporalType.TIMESTAMP)
    private Date regTime;
}
