/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.huobanplus.sns.service;

import com.huotu.huobanplus.sns.entity.Login;
import com.huotu.huobanplus.sns.model.admin.PermissionSearchModel;
import org.springframework.data.domain.Page;

/**
 * Created by 后台用户 on 2016/10/9.
 */
public interface PermissionService {

    /**
     * 根据查询条件获取管理员列表
     * @return
     * @throws Exception
     */
    Page<Login> getManagerList(PermissionSearchModel model) throws Exception;

    /**
     * 保存
     * @param login
     * @throws Exception
     */
    void addPermission(Login login) throws Exception;

    /**
     * 更新
     * @param login
     * @throws Exception
     */
    void updatePermission(Login login) throws Exception;

}
