package com.huotu.huobanplus.sns.mallentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/10/31.
 */
@Entity
@Setter
@Getter
@Cacheable(value = false)
@Table(name = "Swt_CustomerManage")
public class MallMerchant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SC_UserID")
    private Long id;

    /**
     * 登录名
     */
    @Column(name = "SC_UserLoginName", nullable = false, length = 50)
    private String loginName;


    /**
     * 密码
     * 保存的是一次md5以后的小写hex
     */
    @Column(name = "SC_UserLoginPassword", length = 32)
    @JsonIgnore
    private String loginPassword;


    /**
     * 昵称
     */
    @Column(name = "SC_UserNickName", length = 200)
    private String nickName;


    /**
     * 手机号
     */
    @Column(name = "SC_PhoneNumber")
    private String mobile;

    /**
     * 经过核查，数据库内存有为null的SC_MallStatus
     * 为null暂时没有语义。
     */
    @Column(name = "SC_MallStatus")
    private Boolean enabled;

    /**
     * 二级域名 如huotu
     *
     * @since 1.3
     */
    @Column(name = "SC_SubDomain")
    private String subDomain;
}
