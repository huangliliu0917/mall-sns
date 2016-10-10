package com.huotu.huobanplus.sns.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Entity;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by luffy on 2015/6/10.
 * 系统管理员
 *
 * @author luffy luffy.ja at gmail.com
 */
@Entity
public class Manager extends AbstractLogin{
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(
                new SimpleGrantedAuthority("ROLE_ALL")

        );
    }
}