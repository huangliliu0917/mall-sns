/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.entity.support;

import com.huotu.huobanplus.sns.model.AuthenticationTypeModel;
import org.springframework.data.rest.core.annotation.Description;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jin on 2016/10/13.
 */
public class AuthenticationType {

    @Description("普通用户")
    public static final int normal = 0;
    @Description("组长")
    public static final int leader = 1;
    @Description("管理员")
    public static final int manager = 2;
    private static final List<AuthenticationTypeModel> allTypes;

    static {
        allTypes = new ArrayList<>();
        for (Field field : AuthenticationType.class.getFields()) {
            try {
                AuthenticationTypeModel model = new AuthenticationTypeModel();
                model.setId(field.getInt(AuthenticationType.class));
                model.setName(field.getAnnotation(Description.class).value());
                allTypes.add(model);
            } catch (IllegalAccessException e) {
                throw new InternalError(e);
            }
        }
    }

    public static List<AuthenticationTypeModel> allTypes() {
        return allTypes;
    }

    public static int defaultType() {
        return AuthenticationType.normal;
    }

    public static String getDescription(int type) {
        for (AuthenticationTypeModel model : allTypes) {
            if (model.getId() == type) return model.getName();
        }
        return null;
    }
}
