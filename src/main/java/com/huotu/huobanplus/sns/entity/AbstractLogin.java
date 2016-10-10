package com.huotu.huobanplus.sns.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Date;

/**
 * 后台登录用户
 * Created by slt on 2016/10/9.
 */
@Entity
@Getter
@Setter
public abstract class AbstractLogin implements UserDetails {
    private static final long serialVersionUID = -349012453592429794L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 账号名称
     */
    @Column
    private String loginName;
    /**
     * 账号密码
     */
    private String password;
    /**
     * 是否可用
     */
    private boolean enabled = true;

    /**
     * 上次登录时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginDate;
    /**
     * 上次登录IP
     */
    private String lastLoginIP;

    @Override
    public String getUsername() {
        return loginName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
