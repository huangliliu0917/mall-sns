/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2015. All rights reserved.
 */

package com.huotu.huobanplus.sns.service;


public interface CommonConfigService {


    /**
     * 获取当前网站的地址
     *
     * @return
     */
    String getWebUrl();

    /**
     * 授权网站域名地址
     *
     * @return
     */
    String getAuthWebUrl();


    /**
     * 传输加密的密钥
     *
     * @return
     */
    String getAuthKeySecret();

    /**
     * 静态资源域名地址
     *
     * @return
     */
    String getResourcesUri();

    /**
     * 上传资源的服务地址
     *
     * @return
     */
    String getResourcesHome();

    /***
     * 获得对应商家的Id
     */
    String getCustomerId();

}
