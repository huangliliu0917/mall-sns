/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.utils;

import org.springframework.ui.ModelMap;

import java.util.Objects;

/**
 * Created by jin on 2016/10/11.
 */
public class ResultUtil {

    public static ModelMap success(String msg, Object data) {
        ModelMap map = new ModelMap();
        map.addAttribute("success", true);
        if (Objects.nonNull(data)) {
            map.addAttribute("data", data);
        }
        if (Objects.nonNull(msg)) {
            map.addAttribute("msg", msg);
        }
        return map;
    }

    public static ModelMap success(String msg) {
        return success(msg, null);
    }

    public static ModelMap success() {
        return success(null, null);
    }

    public static ModelMap failure(String msg) {
        ModelMap map = new ModelMap();
        if (Objects.nonNull(msg)) {
            map.addAttribute("msg", msg);
        }
        return map;
    }

    public static ModelMap failure(String msg, Object data) {
        ModelMap map = new ModelMap();
        map.addAttribute("success", false);
        if (Objects.nonNull(data)) {
            map.addAttribute("data", data);
        }
        if (Objects.nonNull(msg)) {
            map.addAttribute("msg", msg);
        }
        return map;
    }

    public static ModelMap failure() {
        return failure(null);
    }
}
