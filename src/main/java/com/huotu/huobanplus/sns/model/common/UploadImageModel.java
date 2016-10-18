package com.huotu.huobanplus.sns.model.common;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/10/17.
 */
@Getter
@Setter
public class UploadImageModel {
    private int error;

    private int code;

    private String message;

    private String url;

    private Object data;
}
