package com.huotu.huobanplus.sns.boot;


import com.huotu.huobanplus.sns.model.AppPublicModel;

/**
 * 公共参数持有者
 *
 * @author CJ
 */
public class PublicParameterHolder {

    private static final ThreadLocal<AppPublicModel> models = new ThreadLocal<>();
    /**
     * 获取当前公共参数
     * @return 在controller级别操作 返回总不会为空
     */
    public static AppPublicModel getParameters() {
        return models.get();
    }

    public static void putParameters(AppPublicModel model) {
        models.set(model);
    }
}
