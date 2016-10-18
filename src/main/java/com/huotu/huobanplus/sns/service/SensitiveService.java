/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service;

import java.io.IOException;

/**
 * Created by jin on 2016/10/18.
 * 敏感词接口
 */
public interface SensitiveService {

    /**
     * 判断是否包含敏感词
     *
     * @param content 内容
     * @return 包含则返回true
     * @throws IOException
     */
    boolean ContainSensitiveWords(String content) throws IOException;
}
