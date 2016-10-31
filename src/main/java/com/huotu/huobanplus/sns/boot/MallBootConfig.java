package com.huotu.huobanplus.sns.boot;

import org.luffy.lib.libspring.logging.LoggingConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by Administrator on 2016/10/31.
 */
@Configuration
@ComponentScan({"com.huotu.huobanplus.sns.mallservice"})
@EnableJpaRepositories(value = {"com.huotu.huobanplus.sns.mallrepository"},transactionManagerRef = "mallTransactionManager",entityManagerFactoryRef = "mallEntityManagerFactory")
@ImportResource(value = {"classpath:mall-spring-jpa.xml"})
@Import({LoggingConfig.class})
public class MallBootConfig {

    @Bean
    @Qualifier(value = "mallEntityManager")
    public EntityManager mallEntityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

}
