package com.huotu.huobanplus.sns.service.impl;

import com.huotu.huobanplus.sns.entity.Tag;
import com.huotu.huobanplus.sns.model.admin.AdminTagsModel;
import com.huotu.huobanplus.sns.model.admin.AdminTagsPageModel;
import com.huotu.huobanplus.sns.model.admin.PagingModel;
import com.huotu.huobanplus.sns.repository.TagRespository;
import com.huotu.huobanplus.sns.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/14.
 */
@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagRespository tagRespository;

    public AdminTagsPageModel getAdminTagsList(String name, Integer pageNo, Integer pageSize) {
        AdminTagsPageModel adminTagsPageModel = new AdminTagsPageModel();

        Pageable pageable = new PageRequest(pageNo - 1, pageSize, new Sort(Sort.Direction.ASC, "id"));

        Page<Tag> tags = null;
        if (!StringUtils.isEmpty(name)) {
            tags = tagRespository.findByNameLike(name, pageable);
        } else {
            tags = tagRespository.findAll(pageable);
        }

        adminTagsPageModel.setPage(new PagingModel(pageNo, pageSize, tags.getTotalPages(), tags.getTotalElements()));
        adminTagsPageModel.setList(toAdminTags(tags.getContent()));
        return adminTagsPageModel;
    }

    private List<AdminTagsModel> toAdminTags(List<Tag> tags) {
        List<AdminTagsModel> adminTagsModels = new ArrayList<>();
        tags.forEach(x -> {
            adminTagsModels.add(new AdminTagsModel(x.getId(), x.getName()));
        });
        return adminTagsModels;
    }

    public AdminTagsModel getAdminTags(Integer id) {
        Tag tag = tagRespository.findOne(id);
        if (tag != null) {
            AdminTagsModel adminTagsModel = new AdminTagsModel();
            adminTagsModel.setId(tag.getId());
            adminTagsModel.setName(tag.getName());
            return adminTagsModel;
        }
        return null;
    }

    public void save(Integer id, String name) {
        Tag tag = null;
        if (id != null && id > 0) {
            tag = tagRespository.findOne(id);
        } else {
            tag = new Tag();
        }
        tag.setName(name);
        tagRespository.save(tag);
    }
}
