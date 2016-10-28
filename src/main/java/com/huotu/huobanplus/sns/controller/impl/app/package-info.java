/**
 * App与服务器端交互协议
 * 1.统一请求带customerId参数，以表明其请求的是具体某个商家的数据
 * 2.安全采用JWT方式进行数据的存储，交互的数据存储在header的authentication中
 * <p>
 * Created by Administrator on 2016/9/29.
 */
package com.huotu.huobanplus.sns.controller.impl.app;