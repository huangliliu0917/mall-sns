package com.huotu.huobanplus.sns.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 后台登录用户
 * Created by slt on 2016/10/9.
 */
@Entity
@Getter
@Setter
@Cacheable(false)
public class Login implements UserDetails {
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

    /**
     * 权限字符串列表(格式："aaa|bbb|ccc")
     */
    private String authors="";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] authorsAll=authors.split("\\|");
        List<SimpleGrantedAuthority> simpleGrantedAuthorityList=new ArrayList<>();
        for(String s:authorsAll){
            simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(s));
        }
        return simpleGrantedAuthorityList;
    }

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
