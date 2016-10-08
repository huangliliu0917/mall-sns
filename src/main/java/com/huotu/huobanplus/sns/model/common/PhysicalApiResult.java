package com.huotu.huobanplus.sns.model.common;

/**
 * Created by Administrator on 2016/10/8.
 */

import lombok.Getter;
import lombok.Setter;

/**
 * 更低级别的api响应
 * @author CJ
 */
@Getter
@Setter
public class PhysicalApiResult {

    /**
     *
     系统状态返回：1，成功;0，失败
     */
    private Integer systemResultCode;
    /**
     *
     成功/失败描述
     */
    private String systemResultDescription;
    /**
     *
     逻辑状态返回 ：1成功,0 失败
     */
    private Integer resultCode;

    /**
     * 逻辑状态描述
     */

    private String resultDescription;
    /**
     * 返回具体数据
     */
    private Object resultData;


}