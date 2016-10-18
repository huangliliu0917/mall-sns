package com.huotu.huobanplus.sns.model.admin;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Administrator on 2016/10/14.
 */
@Getter
@Setter
public class AdminArticleModel {
    private Long id;
    private String name;
    private String publisher;
    private Date date;
    private Long click;
    private Long view;
}
