package com.huotu.huobanplus.sns.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by Administrator on 2016/10/10.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("123456")
                .roles("USER","CIRCLE","WIKI","TAG","MESSAGE","RECOMMEND","REPORT","PERMISSION");
    }

    @Autowired
    public void registerSharedAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().antMatchers("/app/**","/js/**","/css/**");//前台请求忽略
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .headers().frameOptions().sameOrigin().and()
                .authorizeRequests()
                .antMatchers("/admin/login.html").permitAll()
                .antMatchers("/top/category/categoryList/0","/admin/circle/circleList.html","/top/circle/articleList/0","/top/slide/getSlideList").hasRole("CIRCLE")
                .antMatchers("/top/category/categoryList/1","/top/circle/articleList/1").hasRole("WIKI")
                .antMatchers("/top/tags/tagsList").hasRole("TAG")
                .antMatchers("/top/user/index","/top/level/index","/top/user/userUpgrade").hasRole("USER")
                .antMatchers("/top/message/pushToDevice","/top/message/pushToUser").hasRole("MESSAGE")
                .antMatchers("/admin/permission/permissionList.html").hasRole("PERMISSION")
                .antMatchers("/admin/report/reportList.html").hasRole("REPORT")
                .antMatchers("/top/user/suggestedFollow").hasRole("RECOMMEND")
                .anyRequest().authenticated()//任何请求都需要验证
                .and()
                .exceptionHandling().accessDeniedPage("/admin/403.html").and()
                .csrf().disable()//关闭csrf功能
                .formLogin()     //表单验证
                .loginPage("/admin/login.html")
                .defaultSuccessUrl("/admin/index.html", true)//登录成功跳转的页面
//                .failureUrl("/admin/loginError")
                .permitAll()
                .and()
                .httpBasic();

    }




}
