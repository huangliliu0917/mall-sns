/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns;

import com.huotu.huobanplus.sns.boot.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by jin on 2016/11/2.
 */
@Configuration
@EnableWebMvc
@Import({BootConfig.class
        , MallBootConfig.class
        , MvcConfig.class
        , ContainerRuntimeConfig.class
        , LocalRuntimeConfig.class
        , RedisConfig.class})
public class SpringTestConfig {
}
