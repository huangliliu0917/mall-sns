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
                .roles("USER","CIRCLE","CATEGORY","WIKI","TAG","MESSAGE","RECOMMEND","REPORT","PERMISSION");
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
                .antMatchers("**/circle/**","/top/slide/**").hasRole("CIRCLE")
                .antMatchers("**/category/**").hasRole("CATEGORY")
                .antMatchers("**/wiki/**").hasRole("WIKI")
                .antMatchers("**/tags/**").hasRole("TAG")
                .antMatchers("**/user/**").hasRole("USER")
                .antMatchers("**/message/**").hasRole("MESSAGE")
                .antMatchers("**/permission/**").hasRole("PERMISSION")
                .antMatchers("**/report/**").hasRole("REPORT")
                .anyRequest().authenticated()//任何请求都需要验证
                .and()
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
