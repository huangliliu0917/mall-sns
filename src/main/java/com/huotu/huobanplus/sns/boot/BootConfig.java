/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.boot;

import org.luffy.lib.libspring.logging.LoggingConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * 配置信息(多商家版安全弃用)
 * Created by lgh on 2016/8/1.
 */

@Configuration
@ComponentScan({"com.huotu.huobanplus.sns.service", "com.huotu.huobanplus.sns.mvc", "com.huotu.huobanplus.sns.converter"})
@EnableJpaRepositories(value = {"com.huotu.huobanplus.sns.repository"}, transactionManagerRef = "transactionManager", entityManagerFactoryRef = "entityManagerFactory")
@ImportResource(value = {"classpath:spring-jpa.xml"})
@Import({LoggingConfig.class})
public class BootConfig {

    @Bean
    @Qualifier(value = "entityManager")
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

}
