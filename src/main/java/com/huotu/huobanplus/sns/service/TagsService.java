package com.huotu.huobanplus.sns.service;

import com.huotu.huobanplus.sns.model.admin.AdminTagsModel;
import com.huotu.huobanplus.sns.model.admin.AdminTagsPageModel;

/**
 * Created by Administrator on 2016/10/14.
 */
public interface TagsService {
    AdminTagsPageModel getAdminTagsList(String name, Integer pageNo, Integer pageSize);

    AdminTagsModel getAdminTags(Integer id);

   void save(Integer id, String name);
}
