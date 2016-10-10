package com.huotu.huobanplus.sns.service.impl;

import com.huotu.huobanplus.sns.entity.AbstractLogin;
import com.huotu.huobanplus.sns.repository.AbstractLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by slt on 2016/10/9.
 *
 * @author slt
 */
@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private AbstractLoginRepository loginRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AbstractLogin login = loginRepository.findByLoginName(username);
        if (login == null)
            throw new UsernameNotFoundException("无效的用户名。");
        login.setLastLoginDate(new Date());
        return login;
    }

    /**
     * 新建一个可登陆用户
     *
     * @param login    准备新建的可登陆用户
     * @param password 明文密码
     */
    public <T extends AbstractLogin> T newLogin(T login, CharSequence password) {
        login.setPassword(passwordEncoder.encode(password));
        login.setEnabled(true);
        return loginRepository.save(login);
    }
}
