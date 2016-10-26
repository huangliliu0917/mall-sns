package com.huotu.huobanplus.sns.model;

import com.huotu.huobanplus.sns.entity.User;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/10/8.
 */

@Getter
@Setter
public class AppPublicModel {
    /**
     * 服务端参数
     * <p>当前用户，如果没有则为空</p>
     */
    private User currentUser;

    /**
     * 服务端参数
     * <p>当前用户设备，必然非空</p>
     */
    private String ip;

//    /**
//     * 商家id
//     */
//    private Long customerId;

//    /**
//     * 微信唯一识别openid
//     */
//    private String openId;

//    /**
//     * 校验openid的sign
//     */
//    private String sign;

//    /***
//     *
//     */
//    private String unionId;

//    /***
//     * 商城UserId
//     */
//    private Long mallUserId;
}
