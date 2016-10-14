package com.huotu.huobanplus.sns.model.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/10/13.
 */
@Getter
@Setter
@AllArgsConstructor
public class PagingModel {
    private Integer pageNo;
    private Integer pageSize;
    private Integer totalPage;
    private Long recordCount;
}
