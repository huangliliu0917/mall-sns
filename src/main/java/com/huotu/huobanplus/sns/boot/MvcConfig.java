package com.huotu.huobanplus.sns.boot;

import com.huotu.common.api.ApiResultHandler;
import com.huotu.common.api.OutputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.util.List;

/**
 * Created by Administrator on 2016/8/20.
 */
@Configuration
@EnableWebMvc
@ComponentScan({"com.huotu.huobanplus.sns.controller"})
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private Environment environment;


    @Autowired
    private AppCommonInterceptor appCommonInterceptor;

    @Bean
    AppCommonInterceptor appCommonInterceptor() {
        return new AppCommonInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(appCommonInterceptor).addPathPatterns("/app/*");
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new AppHandlerExceptionResolver());
    }


    /**
     * 设置控制器方法参数化输出
     *
     * @param argumentResolvers
     */
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new OutputHandler());
    }

    /**
     * 监听 控制器的ApiResult返回值
     *
     * @param returnValueHandlers
     */
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(new ApiResultHandler());

    }


    /**
     * thymeleaf解析
     *
     * @return
     */
    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        SpringTemplateEngine engine = new SpringTemplateEngine();
        ServletContextTemplateResolver rootTemplateResolver = new ServletContextTemplateResolver();
        rootTemplateResolver.setPrefix("/WEB-INF/content/");
        rootTemplateResolver.setSuffix(".html");
        rootTemplateResolver.setCharacterEncoding("UTF-8");

        if (environment.acceptsProfiles("development")) {
            rootTemplateResolver.setCacheable(false);
        }

        engine.setTemplateResolver(rootTemplateResolver);
        resolver.setTemplateEngine(engine);
        resolver.setOrder(100);
        resolver.setCharacterEncoding("UTF-8");
        resolver.setContentType("text/html;charset=utf-8");
        return resolver;
    }

}