package com.huotu.huobanplus.sns.boot;

import org.luffy.lib.libspring.logging.LoggingConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by lgh on 2016/8/1.
 */

@Configuration
@ComponentScan({"com.huotu.huobanplus.sns.service"})
@EnableJpaRepositories(value = {"com.huotu.huobanplus.sns.repository"})
@ImportResource(value = {"classpath:spring-jpa.xml"})
@Import(LoggingConfig.class)
public class BootConfig {

}
