package com.huotu.huobanplus.sns.service;

import com.huotu.huobanplus.sns.model.admin.AdminTagsModel;
import com.huotu.huobanplus.sns.model.admin.AdminTagsPageModel;
import com.huotu.huobanplus.sns.model.common.TagsType;

import java.util.List;

/**
 * Created by Administrator on 2016/10/14.
 */
public interface TagsService {
    AdminTagsPageModel getAdminTagsList(String name, Integer pageNo, Integer pageSize);

    AdminTagsModel getAdminTags(Integer id);

   void save(Integer id, String name);

    List<AdminTagsModel> addTags(Integer tagsType,Long id, String tagsIds);

    void deleteRags(Integer tagsType, Long id, Integer tagsId);
}
