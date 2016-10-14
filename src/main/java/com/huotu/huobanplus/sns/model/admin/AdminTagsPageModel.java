package com.huotu.huobanplus.sns.model.admin;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Administrator on 2016/10/14.
 */
@Getter
@Setter
public class AdminTagsPageModel extends AdminBasePageModel{
    private List<AdminTagsModel> list;
}
